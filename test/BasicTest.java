import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import util.GraphUtils;
import dataStructure.Graph;

/**
 * 基本测试
 * 
 * @author Zoffy Zhang
 */
public class BasicTest
{
	@Test
	public void generateRegularGraphTest()
	{
		// 测试度数从1到10的正则图是否正确
		for (int degree = 1; degree < 10; degree++)
		{
			Graph g = GraphUtils.generateRegularGraph(degree + 1);
			// 判断边数是否正确
			assertEquals(g.E(), (degree + 1) * degree / 2);
			// 正则图的生成情况符合期望
			// System.out.println(g.toString());
			// System.out.println();

		}
	}

	@Test
	public void isLinearForestTest()
	{
		// true linear forest
		Graph g1 = new Graph(5);
		g1.addEdge(0, 1);
		g1.addEdge(1, 4);
		g1.addEdge(2, 3);
		assertTrue(GraphUtils.isLinearForest(g1));

		// false linear forest
		g1.addEdge(3, 4);
		g1.addEdge(2, 4);
		assertFalse(GraphUtils.isLinearForest(g1));

		// removeEdge Test
		g1.removeEdge(3, 4);
		g1.removeEdge(2, 4);
		assertTrue(GraphUtils.isLinearForest(g1));

	}

	@Test
	public void isLinearForestTest2()
	{
		// the whole graph is a circle itself
		Graph g1 = new Graph(4);
		g1.addEdge(0, 1);
		g1.addEdge(1, 2);
		g1.addEdge(2, 3);
		g1.addEdge(3, 0);
		assertFalse(GraphUtils.isLinearForest(g1));

	}

	// @Test
	// public void isLinearTreeTest()
	// {
	//
	// // true linear tree
	// Graph g1 = new Graph(5);
	// g1.addEdge(1, 2);
	// g1.addEdge(2, 3);
	// assertTrue(GraphUtils.isLinearTree(g1));
	//
	// // false linear Tree
	// g1.addEdge(0, 4);
	// assertFalse(GraphUtils.isLinearTree(g1));
	//
	// // removeEdge Test
	// g1.removeEdge(0, 4);
	// assertTrue(GraphUtils.isLinearTree(g1));
	//
	// }

}
