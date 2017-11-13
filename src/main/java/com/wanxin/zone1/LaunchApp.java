package com.wanxin.zone1;

import java.io.File;
import java.util.Collection;

import com.graphhopper.jsprit.analysis.toolbox.Plotter;
import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.Jsprit;
import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem.FleetSize;
import com.graphhopper.jsprit.core.problem.job.Service;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import com.graphhopper.jsprit.core.util.Solutions;
import com.graphhopper.jsprit.io.problem.VrpXMLWriter;

/**
 * @author Wanxin
 */

public class LaunchApp {
	public static void main(String[] args) {
		final int Snow_Inches_Index = 0;

		/*
		 * some preparation - create output folder
		 */
		File dir = new File("output");
		// if the directory does not exist, create it
		if (!dir.exists()) {
			System.out.println("creating directory ./output");
			boolean result = dir.mkdir();
			if (result)
				System.out.println("./output created");
		}

		/*
		 * specify type of vehicles
		 */
		VehicleTypeImpl snowplowType = VehicleTypeImpl.Builder.newInstance("SnowplowType")
				.addCapacityDimension(Snow_Inches_Index, 12 * 5).build();
		// 12 inches * 5 miles.
		/*
		 * specify depot in Zone 1 (Laurel Yard)
		 */
		VehicleImpl depot1 = VehicleImpl.Builder.newInstance("depot1").setType(snowplowType)
				.setStartLocation(Location.newInstance(-75.554193, 38.511024)).build();

		VehicleRoutingProblem.Builder vrpBuilder = VehicleRoutingProblem.Builder.newInstance();
		vrpBuilder.addVehicle(depot1);

		// specify streets--------------------------------------------------------------------------------------------//
		// GoogleMap GPS format: (Latitude N, Longitude W);
		// VRP Model coordinate format: (x,y);

		Street SharptownRd = new Street("SharptownRd", new double[] { 38.521966, -75.698226 },
				new double[] { 38.548244, -75.578670 }, 1, 7.27, "primary");
		double[][] ps1 = SharptownRd.ComputePoints(); // ps1: PointSets1
		// System.out.println(ps1.length);
		for (int i = 0; i < ps1.length; i++) {
			String id = "SharptownRd " + String.valueOf(i);
			Service[] ServicesSets = new Service[ps1.length];

			ServicesSets[i] = Service.Builder.newInstance(id).setLocation(Location.newInstance(ps1[i][0], ps1[i][1]))
					.addSizeDimension(Snow_Inches_Index,
							(int) (SharptownRd.Snow_Inches * SharptownRd.miles / ps1.length))
					.build();
			vrpBuilder.addJob(ServicesSets[i]);
		}

		Street ColumbiaRd = new Street("ColumbiaRd", new double[] { 38.519905, -75.680200 },
				new double[] { 38.467103, -75.673445 }, 2, (9.37 - 5.49), "primary");
		double[][] ps2 = ColumbiaRd.ComputePoints();
		// System.out.println(ps2.length);
		for (int i = 0; i < ps2.length; i++) {
			String id = "ColumbiaRd " + String.valueOf(i);
			Service[] ServicesSets = new Service[ps2.length];
			ServicesSets[i] = Service.Builder.newInstance(id).setLocation(Location.newInstance(ps2[i][0], ps2[i][1]))
					.addSizeDimension(Snow_Inches_Index, (int) (ColumbiaRd.Snow_Inches * ColumbiaRd.miles / ps2.length))
					.build();
			vrpBuilder.addJob(ServicesSets[i]);
		}

		Street DelmarRd = new Street("DelmarRd", new double[] { 38.466080, -75.670816 },
				new double[] { 38.456411, -75.583048 }, 3, (5.49 - 0.65), "primary");
		double[][] ps3 = DelmarRd.ComputePoints();
		// System.out.println(ps3.length);
		for (int i = 0; i < ps3.length; i++) {
			String id = "DelmarRd " + String.valueOf(i);
			Service[] ServicesSets = new Service[ps3.length];
			ServicesSets[i] = Service.Builder.newInstance(id).setLocation(Location.newInstance(ps3[i][0], ps3[i][1]))
					.addSizeDimension(Snow_Inches_Index, (int) (DelmarRd.Snow_Inches * DelmarRd.miles / ps3.length))
					.build();
			vrpBuilder.addJob(ServicesSets[i]);
		}

		Street BiStateBlvd = new Street("BiStateBlvd", new double[] { 38.458013, -75.574237 },
				new double[] { 38.535186, -75.569616 }, 4, 6.02, "primary");
		double[][] ps4 = BiStateBlvd.ComputePoints();
		// System.out.println(ps4.length);
		for (int i = 0; i < ps4.length; i++) {
			String id = "BiStateBlvd " + String.valueOf(i);
			Service[] ServicesSets = new Service[ps4.length];
			ServicesSets[i] = Service.Builder.newInstance(id).setLocation(Location.newInstance(ps4[i][0], ps4[i][1]))
					.addSizeDimension(Snow_Inches_Index,
							(int) (BiStateBlvd.Snow_Inches * BiStateBlvd.miles / ps4.length))
					.build();
			vrpBuilder.addJob(ServicesSets[i]);
		}

		Street SussexHwy1 = new Street("SussexHwy1", new double[] { 38.554113, -75.556410 },
				new double[] { 38.456264, -75.558309 }, 5, (13.76 - 6.88), "primary");
		double[][] ps5 = SussexHwy1.ComputePoints();
		// System.out.println(ps5.length);
		for (int i = 0; i < ps5.length; i++) {
			String id = "SussexHwy1 " + String.valueOf(i);
			Service[] ServicesSets = new Service[ps5.length];
			ServicesSets[i] = Service.Builder.newInstance(id).setLocation(Location.newInstance(ps5[i][0], ps5[i][1]))
					.addSizeDimension(Snow_Inches_Index, (int) (SussexHwy1.Snow_Inches * SussexHwy1.miles / ps5.length))
					.build();
			vrpBuilder.addJob(ServicesSets[i]);
		}

		Street SussexHwy2 = new Street("SussexHwy2", new double[] { 38.640054, -75.593506 },
				new double[] { 38.554113, -75.556410 }, 6, (12.61 - 6.32), "primary");
		double[][] ps6 = SussexHwy2.ComputePoints();
		for (int i = 0; i < ps6.length; i++) {
			String id = "SussexHwy2 " + String.valueOf(i);
			Service[] ServicesSets = new Service[ps6.length];
			ServicesSets[i] = Service.Builder.newInstance(id).setLocation(Location.newInstance(ps6[i][0], ps6[i][1]))
					.addSizeDimension(Snow_Inches_Index, (int) (SussexHwy2.Snow_Inches * SussexHwy2.miles / ps6.length))
					.build();
			vrpBuilder.addJob(ServicesSets[i]);
		}

		Street SeafoodRd = new Street("SeafoodRd", new double[] { 38.565849, -75.571805 },
				new double[] { 38.628140, -75.608357 }, 7, (12.54 - 7.74), "primary");
		double[][] ps7 = SeafoodRd.ComputePoints();
		for (int i = 0; i < ps7.length; i++) {
			String id = "SeafoodRd " + String.valueOf(i);
			Service[] ServicesSets = new Service[ps7.length];
			ServicesSets[i] = Service.Builder.newInstance(id).setLocation(Location.newInstance(ps7[i][0], ps7[i][1]))
					.addSizeDimension(Snow_Inches_Index, (int) (SeafoodRd.Snow_Inches * SeafoodRd.miles / ps7.length))
					.build();
			vrpBuilder.addJob(ServicesSets[i]);
		}

		Street CountySeatHwy = new Street("CountySeatHwy", new double[] { 38.570726, -75.562114 },
				new double[] { 38.619980, -75.485251 }, 8, (6.09 - 0.63), "primary");
		double[][] ps8 = CountySeatHwy.ComputePoints();
		for (int i = 0; i < ps8.length; i++) {
			String id = "CountySeatHwy " + String.valueOf(i);
			Service[] ServicesSets = new Service[ps8.length];
			ServicesSets[i] = Service.Builder.newInstance(id).setLocation(Location.newInstance(ps8[i][0], ps8[i][1]))
					.addSizeDimension(Snow_Inches_Index,
							(int) (CountySeatHwy.Snow_Inches * CountySeatHwy.miles / ps8.length))
					.build();
			vrpBuilder.addJob(ServicesSets[i]);
		}

		Street LaurelRd = new Street("LaurelRd", new double[] { 38.555047, -75.555834 },
				new double[] { 38.524532, -75.408766 }, 7, (17.56 - 8.58), "primary");
		double[][] ps9 = LaurelRd.ComputePoints();
		for (int i = 0; i < ps9.length; i++) {
			String id = "LaurelRd " + String.valueOf(i);
			Service[] ServicesSets = new Service[ps9.length];
			ServicesSets[i] = Service.Builder.newInstance(id).setLocation(Location.newInstance(ps9[i][0], ps9[i][1]))
					.addSizeDimension(Snow_Inches_Index, (int) (LaurelRd.Snow_Inches * LaurelRd.miles / ps9.length))
					.build();
			vrpBuilder.addJob(ServicesSets[i]);
		}

		Street ConcordRd = new Street("ConcordRd", new double[] { 38.640910, -75.592735 },
				new double[] { 38.620598, -75.485609 }, 6, 6.92, "primary");
		double[][] ps10 = ConcordRd.ComputePoints();
		for (int i = 0; i < ps10.length; i++) {
			String id = "ConcordRd " + String.valueOf(i);
			Service[] ServicesSets = new Service[ps10.length];
			ServicesSets[i] = Service.Builder.newInstance(id).setLocation(Location.newInstance(ps10[i][0], ps10[i][1]))
					.addSizeDimension(Snow_Inches_Index, (int) (ConcordRd.Snow_Inches * ConcordRd.miles / ps10.length))
					.build();
			vrpBuilder.addJob(ServicesSets[i]);
		}

		Street HardscrabbleRd = new Street("HardscrabbleRd", new double[] { 38.620139, -75.484224 },
				new double[] { 38.599219, -75.319099 }, 5, (10.42 - 8.26), "primary");
		double[][] ps11 = HardscrabbleRd.ComputePoints();
		for (int i = 0; i < ps11.length; i++) {
			String id = "HardscrabbleRd " + String.valueOf(i);
			Service[] ServicesSets = new Service[ps11.length];
			ServicesSets[i] = Service.Builder.newInstance(id).setLocation(Location.newInstance(ps11[i][0], ps11[i][1]))
					.addSizeDimension(Snow_Inches_Index,
							(int) (HardscrabbleRd.Snow_Inches * HardscrabbleRd.miles / ps11.length))
					.build();
			vrpBuilder.addJob(ServicesSets[i]);
		}

		Street ShilohChurchRd = new Street("ShilohChurchRd", new double[] { 38.554299, -75.531763 },
				new double[] { 38.592634, -75.410491 }, 4, 7.27, "secondary");
		double[][] ps12 = ShilohChurchRd.ComputePoints();
		for (int i = 0; i < ps12.length; i++) {
			String id = "ShilohChurchRd " + String.valueOf(i);
			Service[] ServicesSets = new Service[ps12.length];
			ServicesSets[i] = Service.Builder.newInstance(id).setLocation(Location.newInstance(ps12[i][0], ps12[i][1]))
					.addSizeDimension(Snow_Inches_Index,
							(int) (ShilohChurchRd.Snow_Inches * ShilohChurchRd.miles / ps12.length))
					.build();
			vrpBuilder.addJob(ServicesSets[i]);
		}

		Street WhitesvilleRd = new Street("WhitesvilleRd", new double[] { 38.501877, -75.556895 },
				new double[] { 38.461270, -75.384359 }, 3, 7.27, "secondary");
		double[][] ps13 = WhitesvilleRd.ComputePoints();
		for (int i = 0; i < ps13.length; i++) {
			String id = "WhitesvilleRd " + String.valueOf(i);
			Service[] ServicesSets = new Service[ps13.length];
			ServicesSets[i] = Service.Builder.newInstance(id).setLocation(Location.newInstance(ps13[i][0], ps13[i][1]))
					.addSizeDimension(Snow_Inches_Index,
							(int) (WhitesvilleRd.Snow_Inches * WhitesvilleRd.miles / ps13.length))
					.build();
			vrpBuilder.addJob(ServicesSets[i]);
		}

		// --------------------------------------------------------------------------------------------------//
		vrpBuilder.setFleetSize(FleetSize.INFINITE);
		VehicleRoutingProblem problem = vrpBuilder.build();

		/*
		 * create the algorithm
		 */
		VehicleRoutingAlgorithm algorithm = Jsprit.createAlgorithm(problem);

		/*
		 * collect all solutions
		 */
		Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();

		/*
		 * get the best solution
		 */
		VehicleRoutingProblemSolution bestSolution = Solutions.bestOf(solutions);

		new VrpXMLWriter(problem, solutions).write("output/solution.xml");

		/*
		 * plot
		 */
		new Plotter(problem, bestSolution).plot("output/plot.png", "Zone 1");

		/*
		 * render problem and solution with GraphStream
		 */
		new GraphViewer(problem, bestSolution).setRenderDelay(50).display();
	}

}
