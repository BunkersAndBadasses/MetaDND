package entity;

//Class may or may not get used, depends what we want to do with dungeons
public class DungeonEntity extends DNDEntity{

	String imageFilePath;

	@Override
	public void search(String searchString, Thread runningThread) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	public String getImageFilePath() {
		return imageFilePath;
	}

	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}

}
