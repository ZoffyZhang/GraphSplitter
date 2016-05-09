package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
import core.Splitter;
import dataStructure.Graph;

/**
 * 大量分解正则图，将结果输出到result文件夹中
 * 
 * @author Zoffy Zhang
 */
public class MassivelySplit
{
	public static void main(String[] args) throws IOException
	{
		// 分解的度数范围
		int min = 0;
		int max = 100;

		// 从控制台输入范围
		System.out.println("请输入要分解的正则图的度数范围（如0 100）:");
		Scanner scanner = new Scanner(System.in);
		min = scanner.nextInt();
		max = scanner.nextInt();
		scanner.close();

		// 创造目录和文件
		String NEWLINE = System.getProperty("line.separator");
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd - kk.mm.ss");
		String datetime = df.format(new Date());
		String dir = System.getProperty("user.dir") + File.separator + "result" + File.separator
				+ datetime + " - range " + min + "," + max;
		String dir2 = dir + File.separator + "linear forest";
		FileWriter fw_all = null;
		FileWriter fw_true = null;
		FileWriter fw_false = null;
		FileWriter fw_forest = null;
		File file_all = new File(dir, "LAC-all.txt");		// 输出全部结果
		File file_true = new File(dir, "LAC-true.txt");		// 只输出符合猜想的部分
		File file_false = new File(dir, "LAC-false.txt");	// 只输出不符合猜想的部分
		File file_forest = null;							// 输出线性森林
		if (!file_all.exists())
			file_all.getParentFile().mkdirs();
		fw_all = new FileWriter(file_all);
		fw_true = new FileWriter(file_true);
		fw_false = new FileWriter(file_false);

		// 分解从min到max度的正则图
		String string;
		for (int degree = min; degree <= max; degree++)
		{
			// 记录开始时间
			long start = System.currentTimeMillis();
			// 开始工作
			Splitter splitter = new Splitter(degree);
			int arboricity = splitter.getArboricity();
			// 线性荫度猜想 Linear Arboricity Conjecture(LAC):
			// ceil(∆/2) ≤ la(G) ≤ ceil((∆+1)/2)
			int lExpectation = (int) Math.ceil(degree / 2.0);
			int rExpectation = (int) Math.ceil((degree + 1) / 2.0);
			// 验证LAC，将符合期望、不符合期望的结果分别写入文件中，file_all存有全部的结果
			if (lExpectation <= arboricity && arboricity <= rExpectation)
			{
				string = "LAC TRUE: degree=" + degree + "		expected la(G)=" + rExpectation
						+ "		computed la(G)=" + arboricity;
				fw_all.write(string);
				fw_true.write(string + NEWLINE);
				System.out.println(string);
			}
			else
			{
				string = "LAC FALSE: degree=" + degree + "		expected la(G)=" + rExpectation
						+ "		computed la(G)=" + arboricity;
				fw_all.write(string);
				fw_false.write(string + NEWLINE);
				System.out.println(string);
			}

			// 创建记录线性森林的文件
			file_forest = new File(dir2, degree + ".txt");
			if (!file_forest.exists())
				file_forest.getParentFile().mkdir();
			fw_forest = new FileWriter(file_forest);
			LinkedList<Graph> forest = splitter.getForests();
			for (Graph f : forest)
			{
				fw_forest.write(f.toString());
				fw_forest.write(NEWLINE + "--------------------------" + NEWLINE + NEWLINE);
			}
			fw_forest.close();

			// 记录当前时间
			long now = System.currentTimeMillis();
			// 计算运行花费的时间
			double timeSpent = (now - start) / 1000.0;
			string = "		Time Spent: " + timeSpent + " seconds" + NEWLINE;
			// 记录花费的时间到文件中
			fw_all.write(string);
			System.out.println(string);
		}
		System.out.println("-------------All Done !-------------");

		fw_all.close();
		fw_true.close();
		fw_false.close();

	}
}
