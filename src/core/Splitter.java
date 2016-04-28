package core;

import java.util.LinkedList;
import java.util.Stack;
import util.GraphUtils;
import dataStructure.Graph;

/**
 * 分解一个的度数为degree的正则图至线性森林
 * 
 * @author Zoffy Zhang
 */
public class Splitter
{
	private Graph graph;
	private LinkedList<Graph> trees = new LinkedList<Graph>();
	private LinkedList<Graph> forest = new LinkedList<Graph>();
	private LinkedList<Graph> toBeUnion = new LinkedList<Graph>();

	@SuppressWarnings("unused")
	private Splitter()
	{}

	/**
	 * 分解一个正则无向图
	 * 
	 * @param degree
	 *            正则图的度数
	 */
	public Splitter(int degree)
	{
		if (degree < 0)
			throw new IllegalArgumentException("degree can not be negative!");
		graph = GraphUtils.generateRegularGraph(degree + 1);
		splitToTrees();
		unionToLForest();
	}

	/**
	 * 如果需要分解一个自定义的图，请用此接口 <br>
	 * 
	 * @param g
	 *            一个任意的简单无向图
	 */
	public Splitter(Graph g)
	{
		graph = g;
		splitToTrees();
		unionToLForest();
	}

	/**
	 * 将图分解成多个线性树
	 * <p>
	 * graph -> trees
	 * 
	 */
	private void splitToTrees()
	{
		// 选择一个邻边非空的点作为搜索起点
		int s = GraphUtils.notEmptyVertex(graph);
		// 若图为空，分解结束
		if (s == -1)
			return;

		// 图不为空
		else
		{
			DepthFirstPaths search = new DepthFirstPaths(graph, s);

			// 找到最长的一条线性树，长度一样的以先找到的为结果
			int longest = -1;
			int compare = 1;
			for (int v = 0; v < graph.V(); v++)
			{
				if (search.hasPathTo(v))
				{
					int curLength = search.pathTo(v).size();
					if (curLength > compare)
					{
						compare = curLength;
						longest = v;
					}
				}
			}

			// 生成最长线性树,并从原图删除该线性树
			if (longest != -1)
			{
				Stack<Integer> stack = search.pathTo(longest);
				Graph ltree = new Graph(graph.V());
				while (stack.size() > 1)
				{
					int v = stack.pop();
					int w = stack.peek();
					ltree.addEdge(v, w);
					graph.removeEdge(v, w);
				}
				trees.add(ltree);
			}

			// 递归进行分解
			splitToTrees();
		}
	}

	/**
	 * 将线性树组合成线性森林
	 * <p>
	 * trees -> forest
	 * 
	 */
	private void unionToLForest()
	{
		// 将线性树按长度分为两类
		for (int i = 0; i < trees.size(); i++)
		{
			if (trees.get(i).E() == graph.V() - 1)
				forest.add(trees.get(i));
			else
				toBeUnion.add(trees.get(i));
		}

		union();
	}

	/**
	 * 将toBeUnion中的线性树组合成森林，直到toBeUnion为空
	 */
	private void union()
	{
		int size = toBeUnion.size();

		if (size < 1)
			return;
		else if (size == 1)
			forest.add(toBeUnion.pollFirst());
		else
		{
			// 采用两两握手的方式，尝试出最佳组合结果
			for (int i = 0; i < size - 1; i++)
			{
				// 弹出首部的两棵线性树
				Graph g1 = toBeUnion.pollFirst();
				Graph g2 = toBeUnion.pollFirst();
				// 将g1,g2合并进tmp
				Graph tmp = new Graph(g2);
				for (int v = 0; v < graph.V(); v++)
				{
					if (g1.adj(v).size() == 1)
					{
						int w = g1.adj(v).getFirst();
						// v-w边不存在，则添加进去
						if (!tmp.connected(v, w))
							tmp.addEdge(v, w);
					}
				}
				// 如果tmp是一个线性森林，添加到toBeUnion首部
				if (GraphUtils.isLinearForest(tmp))
				{
					toBeUnion.add(0, tmp);
				}
				// 否则将g1添加到首部，g2添加到尾部
				else
				{
					toBeUnion.add(0, g1);
					toBeUnion.add(g2);
				}
			}
			// 此时toBeUnion的首元素完成尝试，将其添加到forest中
			forest.add(toBeUnion.pollFirst());
			// 递归
			union();
		}
	}

	/**
	 * 得到线性荫度
	 * 
	 * @return la(G)
	 */
	public int getArboricity()
	{
		return forest.size();
	}

	//
	// -------- Getters for test--------
	//
	public Graph getGraph()
	{
		return graph;
	}

	public LinkedList<Graph> getTrees()
	{
		return trees;
	}

	public LinkedList<Graph> getForest()
	{
		return forest;
	}

}
