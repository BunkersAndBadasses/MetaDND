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
	
	public GridMapper(String filename) {
		this.filename = filename;
		
		try {
			this.reader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			this.writer = new PrintWriter("generatedDungeon.svg", "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		this.svgStart1 = "<svg xmlns=\"http://www.w3.org/2000/svg\" ";
		this.svgStart2 = "xmlns:xlink=\"http://www.w3.org/1999/xlink\">";
				
		//TODO: make this configurable
		this.sizeOfSquare = 30;

		
	}
	
	public boolean generateSVG() {
		
		
		String imageSize = "height=\"";
		imageSize += Integer.toString(this.sizeOfSquare * this.sizeOfSquare);
		imageSize += "\" width=\"";
		imageSize += Integer.toString(this.sizeOfSquare * this.sizeOfSquare);
		imageSize += "\" ";
		
		this.writer.print(this.svgStart1);
		this.writer.print(imageSize);
		this.writer.println(this.svgStart2);
		
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
							svgLine = generateLine(Tile.Unpassable, counter*30, i*30);
							break;
						case "_":
							svgLine = generateLine(Tile.Passable, counter*30, i*30);
							break;
						default:
							break;
					}
					counter++;
					this.writer.println(svgLine + "\n");
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
		String retStr = "<rect x=\"" + Integer.toString(x) + "\" y=\"" + Integer.toString(y);
		retStr += "\" width=\"";
		retStr += Integer.toString(this.sizeOfSquare) + "\" height=\"";
		retStr += Integer.toString(this.sizeOfSquare) + "\" style=\"fill:";
		switch (tileType) {
			case Passable:
				retStr += "blue;stroke:black;stroke-width:5;fill-opacity:0.1;stroke-opacity:0.9\"/>"; 
				break;
			case Unpassable:
				retStr += "rgb(123,105,93);stroke:black;stroke-width:5;fill-opacity:1;stroke-opacity:0.9\"/>"; 
				break;
			default:
				System.out.println("LOL! Error.");
				break;
		}
		return retStr;
	}

}
