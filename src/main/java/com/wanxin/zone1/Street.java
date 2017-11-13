package com.wanxin.zone1;

/**
 * @author Wanxin
 */

public class Street {
	String name;
	double[] start = new double[2];
	double[] end = new double[2];
	int Snow_Inches;
	String Road_Type;
	double miles;

	double primaryPrecision = 0.005;
	double secondaryPrecision = 0.01;

	public Street(String name, double[] start, double[] end, int Snow_Inches, double miles, String Road_Type) {
		this.name = name;
		this.start = start;
		this.end = end;
		this.Snow_Inches = Snow_Inches;
		this.miles = miles;
		this.Road_Type = Road_Type;
	}

	// start[0]: y_1, start[1]:x_1;
	// end[0]: y_2, end[1]:x_2;
	public double[][] ComputePoints() {
		int count1, count2;

		if (Road_Type == "primary") {
			count1 = Math.abs((int) ((end[1] - start[1]) / primaryPrecision));
			count2 = Math.abs((int) ((end[0] - start[0]) / primaryPrecision));
		} else {
			count1 = Math.abs((int) ((end[1] - start[1]) / secondaryPrecision));
			count2 = Math.abs((int) ((end[0] - start[0]) / secondaryPrecision));
		}

		if (count1 < count2) {
			count1 = count2;
		}

		double add_x = (end[1] - start[1]) / count1;
		double add_y = (end[0] - start[0]) / count1;
		// GPS format --> VRP coordinate format.
		double[][] PointSets = new double[count1 + 2][2];
		PointSets[0][0] = start[1];
		PointSets[0][1] = start[0];
		PointSets[count1 + 1][0] = end[1];
		PointSets[count1 + 1][1] = end[0];
		for (int i = 1; i <= count1; i++) {
			PointSets[i][0] = start[1] + add_x * i;
			PointSets[i][1] = start[0] + add_y * i;
		}

		return PointSets;
	}
}
