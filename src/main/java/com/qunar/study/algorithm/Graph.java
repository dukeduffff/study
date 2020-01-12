package com.qunar.study.algorithm;

import java.util.LinkedList;
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
    }

    public static void main(String[] args) {
        AdjacencyList adjacencyList = new AdjacencyList(8, false);
        adjacencyList.addEdge(0, 1);
        adjacencyList.addEdge(0, 3);
        adjacencyList.addEdge(1, 2);
        adjacencyList.addEdge(1, 4);
        adjacencyList.addEdge(3, 4);
        adjacencyList.addEdge(2, 5);
        adjacencyList.addEdge(4, 5);
        adjacencyList.addEdge(4, 6);
        adjacencyList.addEdge(5, 7);
        adjacencyList.addEdge(6, 7);
        adjacencyList.bfs(0, 7);
        System.out.println();
        adjacencyList.dfs(0, 7);
    }
}
