/**
 * 
 */
package XX_plots;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import XX_plots.Plot.DataSeries;

//import appPlots.plot.Plot;

/**
 * @author Yannis Tzitzikas (yannistzitzik@gmail.com)
 *
 */
public class PlotExamples {
	
	static void example1() {
		Plot plot = Plot.plot(null).
			    // setting data
			    series(null, Plot.data().
			        xy(1, 2).
			        xy(3, 4), null);

			// saving sample_minimal.png
			try {
				plot.save("sample_minimal", "png");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (Desktop.isDesktopSupported()) {
				 try {
				     Desktop desktop = Desktop.getDesktop();
				     File myFile = new File("sample_minimal.png");
				     desktop.open(myFile);
				     } catch (IOException ex) {}
			}
	}
	
	static void example2() {
		Plot plot = Plot.plot(Plot.plotOpts().
		        title("Hello World").
		        legend(Plot.LegendFormat.BOTTOM)).
		    xAxis("x", Plot.axisOpts().
		        range(0, 5)).
		    yAxis("y", Plot.axisOpts().
		        range(0, 5)).
		    series("Data", Plot.data().
		        xy(1, 2).
		        xy(3, 4),
		        Plot.seriesOpts().
		            marker(Plot.Marker.DIAMOND).
		            markerColor(Color.GREEN).
		            color(Color.BLACK));

		try {
			plot.save("sample_hello", "png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (Desktop.isDesktopSupported()) {
			 try {
			     Desktop desktop = Desktop.getDesktop();
			     File myFile = new File("sample_hello.png");
			     desktop.open(myFile);
			     } catch (IOException ex) {}
		}
	}

	static void example3() {
		// configuring plot
				Plot plot = Plot.plot(Plot.plotOpts().
						title("Measure everything!").
						titleFont(new Font("Arial", Font.ITALIC, 16)).
						width(1000).
						height(600).
						bgColor(Color.GRAY).
						fgColor(Color.BLUE).
						padding(50). // padding for the whole image
						plotPadding(30). // padding between plot border and the very plot
						labelPadding(20). // padding between label and other elements
						labelFont(new Font("Arial", Font.BOLD, 12)).
						grids(5, 5).
						gridColor(Color.CYAN).
						gridStroke(new BasicStroke(1)).
						tickSize(10).
						legend(Plot.LegendFormat.RIGHT)).
					// configuring axes
					xAxis("x", Plot.axisOpts().
						format(Plot.AxisFormat.TIME_HMS). // use not numbers but HH:MM:SS
						range(0, 5)).
					yAxis("y", Plot.axisOpts().
						range(0, 5)).
					// configuring data series
					series("Data", Plot.data().
						xy(1, 2).
						xy(3, 4),
						Plot.seriesOpts().
							color(Color.BLACK).
							line(Plot.Line.SOLID).
							lineWidth(8).
							marker(Plot.Marker.DIAMOND).
							markerColor(Color.GREEN).
							markerSize(12).
							// not useful here as we have one x and one y axes
							xAxis("x"). // reference to x axis
							yAxis("y"))
							//;
							.
					series("System2", Plot.data().
				    		xy(0.0, 0.7).
							xy(0.1, 0.6).
							xy(0.2, 0.6).
							xy(0.3, 0.5).
							xy(0.4, 0.5).
							xy(0.5, 0.4).
							xy(0.6, 0.3).
							xy(0.7, 0.2).
							xy(0.8, 0).
							xy(0.9, 0).
							xy(1, 0),
							Plot.seriesOpts().
								//line(Line.NONE).
								marker(Plot.Marker.SQUARE).
								markerColor(Color.CYAN));
					
					
					
				try {
					plot.save("ex3", "png");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (Desktop.isDesktopSupported()) {
					 try {
					     Desktop desktop = Desktop.getDesktop();
					     File myFile = new File("ex3.png");
					     desktop.open(myFile);
					     } catch (IOException ex) {}
				}
			
	}
	
	static void exampleGoodConfigurable() {
		
	
		// Data to be visualized as two lists of X,Y values
		// dataset1:
		List<Double> myDataX =  Arrays.asList(1.0,2.0,3.0,4.0,5.5,7.1);
		List<Double> myDataY =  Arrays.asList(3.0,4.0,1.0,2.0,6.0,0.2);
		
		//dataset2:
		List<Double> myDataBX =  Arrays.asList(0.5,2.3,3.0,4.5,5.5,6.1);
		List<Double> myDataBY =  Arrays.asList(5.0,4.0,2.0,2.0,1.0,4.2);
			
		
		Plot plot = Plot.plot(Plot.plotOpts().
				title("Hello World").
				titleFont(new Font("Arial", Font.ITALIC, 16)).
				width(1000).
				height(600).
				bgColor(Color.WHITE).
				fgColor(Color.BLACK).
				padding(50). // padding for the whole image
				plotPadding(0). // padding between plot border and the very plot
				labelPadding(20). // padding between label and other elements
				labelFont(new Font("Arial", Font.BOLD, 16)).
				grids(5, 5).
				gridColor(Color.BLACK).
				gridStroke(new BasicStroke(1)).
				tickSize(10).
				legend(Plot.LegendFormat.RIGHT)).
			// configuring axes
			xAxis("Άξονας Χ", Plot.axisOpts().range(0, 8)).
			yAxis("Άξονας Υ", Plot.axisOpts().range(0, 7)).
			series("System1", 
					Plot.data().xy(myDataX,myDataY),	// the input data as Arraylists here 
				Plot.seriesOpts().
					color(Color.BLACK).
					line(Plot.Line.SOLID).
					lineWidth(6).
					marker(Plot.Marker.DIAMOND).
					markerColor(Color.GREEN).
					markerSize(12)).
			series("System2", Plot.data().xy(myDataBX,myDataBY),	// the input data as Arraylists here 
					Plot.seriesOpts().
						//line(Line.NONE).
						marker(Plot.Marker.SQUARE).
						markerColor(Color.CYAN));
			
		try {
			plot.save("sample_all_opts", "png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		if (Desktop.isDesktopSupported()) {
			 try {
			     Desktop desktop = Desktop.getDesktop();
			     File myFile = new File("sample_all_opts.png");
			     desktop.open(myFile);
			     } catch (IOException ex) {}
		}
		
	}
	
	/**
	 * 
	 */
	public static void main(String[] lala) {
		example1();
		example2();
		example3();
		exampleGoodConfigurable();
	}

}
