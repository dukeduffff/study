package com.qunar.study.algorithm;

import sun.security.provider.certpath.Vertex;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by dujian on 2020/01/11
 * 图相关算法
 */
public class Graph {
    private static class AdjacencyList{//邻接表实现的图
        private int vertexNum;//顶点
        private LinkedList<Integer>[] adjArr;//邻接表
        private boolean directed;//是否是有向图

        public AdjacencyList(int vertexNum, boolean directed) {
            this.vertexNum = vertexNum;
            this.adjArr = new LinkedList[vertexNum];
            this.directed = directed;
            for (int i = 0; i < vertexNum; i++) {
                adjArr[i] = new LinkedList<>();//初始化邻接表
            }
        }

        public void addEdge(int begin, int end) {
            adjArr[begin].add(end);
            if (!directed) {//无向图需要添加另一条边
                adjArr[end].add(begin);
            }
        }

        public int getVertexNum() {
            return vertexNum;
        }

        /**
         * 广度优先搜索
         */
        public void bfs(int beginVertex, int endVertex) {//
            if (beginVertex == endVertex) {
                return;
            }
            boolean[] visited = new boolean[vertexNum];
            Queue<Integer> queue = new LinkedList<>();
            queue.add(beginVertex);
            visited[beginVertex] = true;
            int[] prev = new int[vertexNum];
            while (!queue.isEmpty()) {
                Integer vertex = queue.poll();
                if (vertex == null) {
                    continue;
                }
                for (Integer toVertex : adjArr[vertex]) {
                    if (visited[toVertex]) {
                        continue;
                    }
                    visited[toVertex] = true;//标记访问过
                    prev[toVertex] = vertex;
                    if (toVertex == endVertex) {
                        printPath(prev, beginVertex, endVertex);
                        return;
                    }
                    queue.add(toVertex);
                }
            }
        }

        private void printPath(int[] prev, int beginVertex, int endVertex) {//递归实现
            if (prev == null) {
                return;
            }
            if (beginVertex != endVertex && prev[endVertex] != -1) {
                printPath(prev, beginVertex, prev[endVertex]);
            }
            System.out.print(endVertex + "->");
        }

        /**
         * 深度优先搜索,例如走迷宫
         */
        private void dfs(int beginVertex, int endVertex) {
            boolean[] visited = new boolean[vertexNum];
            int[] prev = new int[vertexNum];
            for (int i = 0; i < prev.length; i++) {
                prev[i] = -1;
            }
            dfsFound(beginVertex, endVertex, visited, prev);
            printPath(prev, beginVertex, endVertex);
        }

        private boolean dfsFound(int beginVertex, int endVertex, boolean[] visited,
                                 int[] prev) {
            visited[beginVertex] = true;
            for (Integer vertex : adjArr[beginVertex]) {
                if (visited[vertex]) {
                    continue;
                }
                prev[vertex] = beginVertex;
                if (endVertex == vertex) {
                    return true;
                }
                boolean found = dfsFound(vertex, endVertex, visited, prev);
                if (found) {
                    return true;
                }
            }
            return false;
        }

        /**
         * 拓扑排序-Kahn算法,有向无环图
         * 判断是不是某个顶点的入度是不是0,是0就可以执行了
         * 如果输出的顶点个数小于实际顶点个数，说明里面有环
         */
        public void topoSortByKahn() {
            int[] inDegree = new int[vertexNum];
            for (int i = 0; i < vertexNum; i++) {
                for (int j = 0; j < adjArr[i].size(); j++) {
                    Integer vertex = adjArr[i].get(j);
                    inDegree[vertex]++;//统计各个顶点的入度
                }
            }
            Queue<Integer> queue = new LinkedList<>();
            for (int i = 0; i < vertexNum; i++) {
                if (inDegree[i] == 0) {
                    queue.add(i);
                }
            }
            while (!queue.isEmpty()) {
                Integer vertex = queue.poll();
                System.out.print(vertex + "->");
                for (int i = 0; i < adjArr[vertex].size(); i++) {
                    Integer v = adjArr[vertex].get(i);
                    inDegree[v]--;
                    if (inDegree[v] == 0) {
                        queue.offer(v);
                    }
                }
            }
        }

        //拓扑排序-dfs算法
        public void topoSortByDfs() {

        }
    }

    public static class DirectGraph {
        private int vertexNum;
        private LinkedList<Edge>[] adjArr;

        public DirectGraph(int vertexNum) {
            this.vertexNum = vertexNum;
            this.adjArr = new LinkedList[vertexNum];
            for (int i = 0; i < vertexNum; i++) {
                adjArr[i] = new LinkedList<>();
            }
        }

        public void addEdge(int s, int t, int weight) {
            this.adjArr[s].add(new Edge(s, t, weight));
        }

        private static class Edge {
            public int sid;
            public int tid;
            public int weight;

            public Edge(int sid, int tid, int weight) {
                this.sid = sid;
                this.tid = tid;
                this.weight = weight;
            }
        }

        private static class Vertex {
            public int id;
            public int distance;//从顶点到该顶点的距离

            public Vertex(int id, int distance) {
                this.id = id;
                this.distance = distance;
            }
        }

        public void dijkstra(int s, int t) {
            int[] predecessor = new int[this.vertexNum];
            PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(new Comparator<DirectGraph.Vertex>() {
                @Override
                public int compare(DirectGraph.Vertex o1, DirectGraph.Vertex o2) {
                    return o1.distance - o2.distance;
                }
            });//优先级队列
            Vertex[] minDistanceV = new Vertex[this.vertexNum];//记录从开始节点到这个节点的距离
            for (int i = 0; i < this.vertexNum; i++) {
                minDistanceV[i] = new Vertex(i, Integer.MAX_VALUE);
            }
            boolean[] inQueue = new boolean[this.vertexNum];
            minDistanceV[s].distance = 0;
            inQueue[s] = true;
            priorityQueue.offer(minDistanceV[s]);
            while (!priorityQueue.isEmpty()) {
                Vertex vertex = priorityQueue.poll();
                if (vertex.id == t) {
                    break;
                }
                for (Edge edge : this.adjArr[vertex.id]) {
                    int distance = vertex.distance + edge.weight;
                    //如果小于之前的距离
                    if (distance < minDistanceV[edge.tid].distance) {
                        predecessor[edge.tid] = edge.sid;
                        minDistanceV[edge.tid].distance = distance;
                        if (inQueue[edge.tid]) {
                            priorityQueue.remove(minDistanceV[edge.tid]);
                            priorityQueue.offer(minDistanceV[edge.tid]);
                        } else {
                            priorityQueue.offer(minDistanceV[edge.tid]);
                            inQueue[edge.tid] = true;
                        }
                    }
                }
            }
            print(s, t, predecessor);
        }

        public void print(int s, int t, int[] predecessor) {
            if (s == t) {
                System.out.print(s);
                return;
            }
            print(s, predecessor[t], predecessor);
            System.out.print("->" + t);
        }
    }

    public static void main(String[] args) {
        AdjacencyList adjacencyList = new AdjacencyList(4, true);
        adjacencyList.addEdge(0, 1);
        adjacencyList.addEdge(1, 2);
        adjacencyList.addEdge(3, 2);
        /*adjacencyList.addEdge(0, 3);
        adjacencyList.addEdge(1, 2);
        adjacencyList.addEdge(1, 4);
        adjacencyList.addEdge(3, 4);
        adjacencyList.addEdge(2, 5);
        adjacencyList.addEdge(4, 5);
        adjacencyList.addEdge(4, 6);
        adjacencyList.addEdge(5, 7);
        adjacencyList.addEdge(6, 7);
        adjacencyList.bfs(0, 7);*/
        System.out.println();
//        adjacencyList.dfs(0, 7);
        adjacencyList.topoSortByKahn();
        System.out.println();
        DirectGraph directGraph = new DirectGraph(6);
        directGraph.addEdge(0, 1, 10);
        directGraph.addEdge(0, 4, 15);
        directGraph.addEdge(1, 2, 15);
        directGraph.addEdge(1, 3, 2);
        directGraph.addEdge(3, 2, 1);
        directGraph.addEdge(3, 5, 12);
        directGraph.addEdge(2, 5, 5);
        directGraph.addEdge(4, 5, 10);
        directGraph.dijkstra(0, 5);
    }
}
