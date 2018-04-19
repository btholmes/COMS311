// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add member fields and additional methods)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may only include libraries of the form java.*)

/**
 * @author Ben Holmes, Anthony House
 */

import ahocorasick.AhoCorasick;
import ahocorasick.SearchResult;
import com.sun.deploy.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WikiCrawler {
    static final String BASE_URL = "https://en.wikipedia.org";
    static String seedUrl;
    static int max;
    static ArrayList<String> topics;
    static String fileName;


    static AhoCorasick tree;
    static HashMap<String, Integer> topicsMap;
    static HashMap<String, LinkedList<String>> graph;
    private ArrayList<String> links;

    private ArrayList<String> traversal;
    private HashMap<String, Integer> map;
    //
    File file;

    public WikiCrawler(String seedUrl, int max, ArrayList<String> topics, String fileName) {
        // implementation
        this.seedUrl = seedUrl;
        this.max = max;
        this.topics = topics;
        this.fileName = fileName;

        tree = getCorasickTree(topics);
        file = new File(fileName);
    }


    private AhoCorasick getCorasickTree(ArrayList<String> topics) {
        AhoCorasick tree = new AhoCorasick();
        for (String topic : topics) {
            topic = topic.toLowerCase();
            String trimmed = topic.trim();
            String result = trimmed;
            tree.add(result.getBytes(), trimmed);
        }
        tree.prepare();

        return tree;
    }

    public void crawl() throws IOException {
        // implementation
        topicsMap = new HashMap<>();
        graph = new HashMap<>();
        traversal = new ArrayList<>();
        writeToFile(max + "", "");
        BFS(seedUrl);
    }

    static int requests = 0;

    private void getLinks(String seedUrl, HashMap<String, Boolean> visited, Queue<String> queue) {
        URL url;
        links = new ArrayList<>();
        try {
            requests++;
            String string = BASE_URL + seedUrl;
            url = new URL(string);
            InputStream is = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            readUrl(seedUrl, br, visited, queue, false);
            br.close();

//            if(requests % 25 == 0) Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readUrl(String seedUrl, BufferedReader br, HashMap<String, Boolean> visited, Queue<String> queue, boolean foundParagraph) throws IOException {
        String line = br.readLine();
        while (line != null) {

            if (!foundParagraph) {
                if (line.contains("<p")) foundParagraph = true;
            }
            if (foundParagraph) {
                getHref(line, visited, queue);
                line = line.toLowerCase();
                checkForMatches(line);
            }
            line = br.readLine();
        }
    }


    private void getHref(String line, HashMap<String, Boolean> visited, Queue<String> queue) {
        Pattern p = Pattern.compile("href=\"(.*?)\"");
        Matcher m = p.matcher(line);
        String url = null;
        while (m.find()) {
            String href = m.group(1);
            if (href.startsWith("/wiki/") && !href.matches(".*[#:].*")) {
                links.add(href);
            }
        }
    }


    private void checkForMatches(String inputStr) {
        Iterator searcher = tree.search(inputStr.getBytes());
        while (searcher.hasNext()) {
            SearchResult result = (SearchResult) searcher.next();
            Set outputs = result.getOutputs();
            Iterator iter = outputs.iterator();
            while (iter.hasNext()) {
                String item = iter.next().toString().toLowerCase();
                if (topicsMap.get(item) == null) {
//                  System.out.println(item + "  was added");
                    topicsMap.put(item, 1);
                }

            }
        }
    }


    static boolean firstLine = true;

    private void writeToFile(String node, String edge) {
        OutputStream os = null;
        try {
            if (firstLine) {
                boolean append = false;
                os = new FileOutputStream(file, append);
                firstLine = false;
            } else {
                boolean append = true;
                os = new FileOutputStream(file, append);
            }
            os.write(node.getBytes(), 0, node.length());
            os.write(" ".getBytes());
            os.write(edge.getBytes());
            os.write("\n".getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void printGraphEdges() {
        Iterator it = graph.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.print(pair.getKey());
//            it.remove(); // avoids a ConcurrentModificationException
        }
        System.out.println();
    }

    private boolean containsAllTopics() {
        boolean result = topicsMap.size() == topics.size();

        topicsMap = new HashMap<>();
        return result;
    }

    private boolean countMet(int count) {
        return count == max;
    }

    private void BFS(String seedUrl) throws IOException {
        int nodeCount = 0;
        // Mark all the vertices as not visited(By default
        HashMap<String, Boolean> visited = new HashMap<>();

        // Create a queue for BFS
        Queue<String> queue = new LinkedList<>();

        visited.put(seedUrl, true);
        queue.add(seedUrl);
        nodeCount++;

        getLinks(seedUrl, visited, queue);
        if (!containsAllTopics()) return;
        graph.put(seedUrl, new LinkedList<>(links));

        while (queue.size() != 0) {
            String node = queue.poll();

//            System.out.println("\n Node is " + node + "\n");
            Iterator<String> edges = graph.get(node).listIterator();
            HashMap<String, Integer> addedValues = new HashMap<>();
            while (edges.hasNext()) {
                String edge = edges.next();
                if (visited.get(edge) == null || visited.get(edge) == false) {
                    visited.put(edge, true);
                    if (nodeCount < max) {
                        getLinks(edge, visited, queue);
                        if (containsAllTopics()) {
                            graph.put(edge, new LinkedList<>(links));
                            queue.add(edge);
                            nodeCount++;
                            writeToFile(node, edge);
                            addedValues.put(edge, 1);
                        }
                    }
                } else {
                    if (graph.containsKey(edge) && !node.equals(edge)) {
                        if (!addedValues.containsKey(edge))
                            writeToFile(node, edge);
                        addedValues.put(edge, 1);
                    }
                }
            }
        }
    }
//=======
//    //Creates a queue of links after the first <p> tag
//    public static LinkedList<String> GetLinksInContent(String html) {
//        LinkedList<String> returnList = new LinkedList<>();
//
//        int startIndex = html.indexOf("<p");
//        int endIndex = html.lastIndexOf("</p>");
//        html = html.substring(startIndex, endIndex);
//
//        Pattern anchorPattern = Pattern.compile("(?i)<a([^>]+)>(.+?)</a>");
//        Pattern hrefPattern = Pattern.compile("\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))");
//        Matcher anchorMatcher = anchorPattern.matcher(html);
//
//        while (anchorMatcher.find()) {
//            String href = anchorMatcher.group(1);     // get the values of href
//            Matcher hrefMatcher = hrefPattern.matcher(href);
//            while (hrefMatcher.find()) {
//                String link = hrefMatcher.group(1);
//                link = link.replaceAll("'", "").replaceAll("\"", "");
//
//                if(!link.startsWith("/wiki/") || link.contains("#") || link.contains(":"))
//                    continue;
//
//                returnList.add(link);
//            }
//        }
//
//        return returnList;
//>>>>>>> 0281eaf72ab2b56c9f75c38d3307e474095ea419
//    }



}