import static org.junit.Assert.assertTrue;
import java.util.LinkedList;
import org.junit.Test;
import util.GraphUtils;
import core.Splitter;
import dataStructure.Graph;

/**
 * 分解器测试
 * 
 * @author Zoffy Zhang
 */
public class SplitterTest
{
	private Splitter splitter;

	@Test
	public void splitToTreesTest()
	{
		// 测试度数从1到10的完全图分解是否符合线性树的标准
		for (int degree = 1; degree < 10; degree++)
		{
			splitter = new Splitter(degree);
			LinkedList<Graph> trees = splitter.getTrees();
			for (int i = 0; i < trees.size(); i++)
			{
				assertTrue(GraphUtils.isLinearForest(trees.get(i)));
				// trees的生成情况符合期望
				// System.out.println(trees.get(i));
			}
		}
	}

	@Test
	public void unionToForestsTest()
	{
		// 测试度数为4的完全图的union情况
		splitter = new Splitter(4);
		LinkedList<Graph> forest = splitter.getForests();
		for (Graph f : forest)
		{
			assertTrue(GraphUtils.isLinearForest(f));
			// forest的生成情况符合期望
			// System.out.println(f.toString());
		}

	}

	@Test
	public void unionToForestsTest2()
	{
		// 测试度数为1到10的完全图的union情况
		for (int degree = 1; degree < 10; degree++)
		{
			splitter = new Splitter(degree);
			LinkedList<Graph> forest = splitter.getForests();
			for (int i = 0; i < forest.size(); i++)
			{
				assertTrue(GraphUtils.isLinearForest(forest.get(i)));
			}
		}
	}

	@Test
	public void degreeTwoTest()
	{
		// 查看度数为2的完全图分解情况

		splitter = new Splitter(2);

		// LinkedList<Graph> trees = splitter.getTrees();
		// LinkedList<Graph> forest = splitter.getForests();

		// System.out.println("--------trees--------");
		// for (Graph g : trees)
		// System.out.println(g);
		//
		// System.out.println("--------forest--------");
		// for (Graph g : forest)
		// System.out.println(g);
	}

	@Test
	public void specialTest()
	{
		// 测试分解https://en.wikipedia.org/wiki/Arboricity上的图，已知其arboricity==3
		Graph g = new Graph(8);
		g.addEdge(0, 3);
		g.addEdge(0, 1);
		g.addEdge(1, 2);
		g.addEdge(1, 6);
		g.addEdge(5, 6);
		g.addEdge(7, 6);
		g.addEdge(4, 7);
		g.addEdge(1, 4);
		g.addEdge(4, 5);
		g.addEdge(0, 5);
		g.addEdge(2, 7);
		g.addEdge(0, 7);
		g.addEdge(2, 5);
		g.addEdge(2, 7);
		g.addEdge(2, 3);
		g.addEdge(3, 6);
		g.addEdge(3, 4);

		splitter = new Splitter(g);
		int arboricity = splitter.getArboricity();
		// System.out.println("specialTest:" + arboricity);
		 assertTrue(arboricity == 3);
	}

	@Test
	public void specialTest2()
	{
		// 测试分解汉密尔顿图--立方体，已知其arboricity==2
		Graph g = new Graph(8);
		g.addEdge(0, 1);
		g.addEdge(0, 3);
		g.addEdge(0, 4);
		g.addEdge(1, 5);
		g.addEdge(1, 2);
		g.addEdge(2, 6);
		g.addEdge(2, 3);
		g.addEdge(3, 7);
		g.addEdge(4, 5);
		g.addEdge(5, 6);
		g.addEdge(7, 6);
		g.addEdge(4, 7);

		splitter = new Splitter(g);
		int arboricity = splitter.getArboricity();
		// System.out.println("HamiltonTest:" + arboricity);
		assertTrue(arboricity == 2);
	}

	@Test
	public void DebugTest()
	{
		// Debug，分析Δ=6的完全图分解过程
		splitter = new Splitter(6);

		// LinkedList<Graph> trees=splitter.getTrees();
		// for (Graph graph : trees)
		// {
		// System.out.println(graph);
		// System.out.println("------------------------------------------");
		// }

		// LinkedList<Graph> forests = splitter.getForests();
		// for (Graph graph : forests)
		// {
		// System.out.println(graph);
		// System.out.println("------------------------------------------");
		//
		// }
		// int arboricity = splitter.getArboricity();
		// System.out.println(arboricity);
	}

}
