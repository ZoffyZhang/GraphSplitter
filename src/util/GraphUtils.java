package util;

import dataStructure.Graph;

/**
 * 
 * @author Zoffy Zhang
 */
public class GraphUtils
{
	/**
	 * k为图的度数。产生k-1完全图，即k正则图
	 * 
	 * @param vertexCount
	 *            正则图的顶点数
	 * @return complete graph
	 */
	public static Graph generateRegularGraph(int vertexCount)
	{
		Graph g = new Graph(vertexCount);
		for (int i = 0; i < vertexCount - 1; i++)
		{
			for (int j = i + 1; j < vertexCount; j++)
			{
				if (!g.connected(i, j))
					g.addEdge(i, j);
			}
		}
		return g;
	}

	/**
	 * @param g
	 * @return 图g为空则返回-1，否则返回一个邻居边非空的点
	 */
	public static int notEmptyVertex(Graph g)
	{
		for (int i = 0; i < g.V(); i++)
		{
			if (g.adj(i).size() > 0)
				return i;
		}
		return -1;
	}

	/**
	 * @return 图g是否为线性森林
	 */
	public static boolean isLinearForest(Graph g)
	{
		int wholeDegree = 0;
		int notEmptyVertex = 0;
		// 从第一个点开始遍历
		return isLinearForest(g, 0, wholeDegree, notEmptyVertex);
	}

	/**
	 * 递归遍历所有的点，判断是否符合线性森林的特征
	 * 
	 * @param g
	 * @param s
	 *            此次遍历的点
	 * @return
	 */
	private static boolean isLinearForest(Graph g, int s, int wholeDegree, int notEmptyVertex)
	{
		// 遍历完成即符合条件
		if (s >= g.V())
			// 平均度数为2，即环
			if (wholeDegree / notEmptyVertex == 2)
				return false;
			else
				return true;

		// 点的度数大于等于3，则不满足条件
		if (g.adj(s).size() >= 3)
			return false;

		// 点的度数为0则遍历下一个点
		else if (g.adj(s).size() == 0)
			return isLinearForest(g, ++s, wholeDegree, notEmptyVertex);
		// 点的度数为1,2
		else
		{
			wholeDegree += g.adj(s).size();
			return isLinearForest(g, ++s, wholeDegree, ++notEmptyVertex);
		}
	}

	// /**
	// *
	// * @param g
	// * @return 图g是否为一个线性树
	// */
	// public static boolean isLinearTree(Graph g)
	// {
	// return isLinearTree(g, 0);
	// }
	//
	// /**
	// * 找到线性树的一个起点
	// *
	// * @param g
	// * @param s
	// * @return
	// */
	// private static boolean isLinearTree(Graph g, int s)
	// {
	// // 遍历完成还没找到度数为1的点，即失败
	// if (s >= g.V())
	// return false;
	//
	// // 点的度数大于等于3，则不满足条件
	// if (g.adj(s).size() >= 3)
	// return false;
	// // 点的度数为0或2，遍历下一个点
	// else if (g.adj(s).size() == 0 || g.adj(s).size() == 2)
	// return isLinearForest(g, s + 1);
	// // 点的度数为1
	// else
	// {
	// Stack<Integer> stack = new Stack<Integer>();
	// stack.push(s);
	// return isLinearTree(g, stack);
	// }
	// }
	//
	// /**
	// * 递归遍历，判断是否线性树 <br>
	// * ps. 要按线性遍历的方式判断一个图是否线性树还是挺麻烦的
	// *
	// * @param g
	// * @param stack
	// * @return
	// */
	// private static boolean isLinearTree(Graph g, Stack<Integer> stack)
	// {
	// // v为当前的点，w为上一个点
	// int v = stack.peek();
	//
	// if (g.adj(v).size() >= 3)
	// return false;
	// // 首部的情况
	// if (stack.size() == 1)
	// {
	// int w = g.adj(v).get(0);
	// stack.push(w);
	// return isLinearTree(g, stack);
	// }
	// // 尾部的情况
	// else if (stack.size() > 1 && g.adj(v).size() == 1)
	// {
	// return true;
	// }
	// else
	// {
	// stack.pop();
	// int w = stack.peek();
	// if (g.adj(v).get(0) == w)
	// {
	// w = g.adj(v).get(1);
	// stack.push(w);
	// return isLinearTree(g, stack);
	// }
	// else
	// {
	// stack.push(g.adj(v).get(0));
	// return isLinearTree(g, stack);
	// }
	//
	// }
	// }
}
