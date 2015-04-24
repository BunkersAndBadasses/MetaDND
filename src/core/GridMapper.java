package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import core.DungeonConstants.Tile;

public class GridMapper {
	
	private String filename;
	private PrintWriter writer;
	private BufferedReader reader;
	private int sizeOfSquare;
	
	private String svgStart1, svgStart2;
	
	public GridMapper(String filename, int sizeOfSquare) {
		this.filename = filename;
		
		try {
			this.reader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			this.writer = new PrintWriter(DungeonConstants.SAVEDDUNGEONSDIR + "//generatedDungeon.svg", "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		this.svgStart1 = "<svg xmlns=\"http://www.w3.org/2000/svg\" ";
		this.svgStart2 = "xmlns:xlink=\"http://www.w3.org/1999/xlink\">";
				
		//TODO: make this configurable
		this.sizeOfSquare = sizeOfSquare;

		
	}
	
	public boolean generateSVG() {
		
		
		String imageSize = "height=\"";
		imageSize += Integer.toString(this.sizeOfSquare * this.sizeOfSquare);
		imageSize += "\" width=\"";
		imageSize += Integer.toString(this.sizeOfSquare * this.sizeOfSquare);
		imageSize += "\" ";
		int sizeOfStep = this.sizeOfSquare / 6;
		
		this.writer.print(this.svgStart1);
		this.writer.print(imageSize);
		this.writer.println(this.svgStart2);
		this.writer.println("<defs>\n<g id = \"downstairs\">");
		this.writer.print("<rect x=\"0\" y=\"0\" width=\"");
		this.writer.print(this.sizeOfSquare + "\" height=\"");
		this.writer.print(this.sizeOfSquare + "\" style=\"fill:tan;stroke:black;stroke-width:" + sizeOfStep);
		this.writer.println(";fill-opacity:1;stroke-opacity:0.9\"/>");
		for (int i = 1; i < 5; i++) {
				this.writer.print("<line x1=\"");
				this.writer.print(sizeOfStep*i + "\" y1=\"" + sizeOfStep*i + "\" x2=\"");
				this.writer.println(sizeOfStep*(i+1) + "\" y2=\"" + sizeOfStep*i + "\" style=\"stroke:black;stroke-width:2\" />");
				this.writer.print("<line x1=\"" + sizeOfStep*(i+1) + "\" y1=\"" + sizeOfStep*i);
				this.writer.println("\" x2=\"" + sizeOfStep*(i+1) + "\" y2=\"" + sizeOfStep*(i+1) + "\" style=\"stroke:black;stroke-width:2\" />");
		}
		
		this.writer.println("</g>");
		this.writer.println("<g id = \"unpassableTerrain\">");
		this.writer.print("<rect x=\"0\" y=\"0\" width=\"" + this.sizeOfSquare);
		this.writer.print("\" height=\"" + this.sizeOfSquare + "\" style=\"fill:rgb(123,105,93);stroke:black;stroke-width:");
		this.writer.print(sizeOfStep + ";fill-opacity:1;stroke-opacity:0.9\"/>");
		this.writer.println("</g>");
		this.writer.println("<g id = \"passableTerrain\">\n");
		this.writer.print("<rect x=\"0\" y=\"0\" width=\"" + this.sizeOfSquare);
		this.writer.print("\" height=\"" + this.sizeOfSquare + "\" style=\"fill:white;stroke:black;stroke-width:");
		this.writer.println(sizeOfStep + ";fill-opacity:1;stroke-opacity:0.9\"/>");
		this.writer.println("</g>");
		
		this.writer.println("</defs>");
		
		int size;
		String line;
		try {
			
			line = this.reader.readLine();
			size = Integer.parseInt(line);
			
			String svgLine = "";
			for (int i = 0; i < size; i++) {
				line = this.reader.readLine();
				int counter = 0;
				
				for (String s: line.split(", ")) {
					switch (s)  {
						case "X":
							svgLine = generateLine(Tile.Unpassable, counter*this.sizeOfSquare, i*this.sizeOfSquare);
							break;
						case "_":
							svgLine = generateLine(Tile.Passable, counter*this.sizeOfSquare, i*this.sizeOfSquare);
							break;
						case "S":
							svgLine = generateLine(Tile.Downstairs, counter*this.sizeOfSquare, i*this.sizeOfSquare);
							break;
						case "U":
							svgLine = generateLine(Tile.Upstairs, counter*this.sizeOfSquare, i*this.sizeOfSquare);
							break;
						default:
							break;
					}
					counter++;
					this.writer.println(svgLine);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	    
		this.writer.println("</svg>");
		this.writer.close();
		return true;
	}
	
	public void setSizeOfSquare(int newSize) {
		this.sizeOfSquare = newSize;
	}
	
	public String generateLine(Tile tileType, int x, int y) {
		String retStr = "<use xlink:href=\"";
		switch (tileType) {
			case Passable:
				retStr += "#passableTerrain\"";
				break;
			case Unpassable:
				retStr += "#unpassableTerrain\"";
				break;
			case Downstairs:
				retStr += "#downstairs\"";
				break;
			case Upstairs:
				retStr += "#downstairs\""; //TODO: fix this.
				break;
			default:
				System.out.println(tileType);
				break;
		}
		retStr += " x=\"" + Integer.toString(x) + "\" y=\"" + Integer.toString(y) + "\" />";
		return retStr;
	}

}
