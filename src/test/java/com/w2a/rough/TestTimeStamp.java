package com.w2a.rough;

import java.util.Date;

public class TestTimeStamp {
	
	public static void main(String args[]) {
		
		Date d = new Date();
		String screenShotName = d.toString().replace(":", "_").replace(" ", "_");
		System.out.println(screenShotName);
		System.out.println(d);
		
	}

}
