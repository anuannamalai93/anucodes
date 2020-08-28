package demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class addtocart {

	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\Downloads\\sel\\chromedriver_win32\\chromedriver.exe");	
		WebDriver driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		WebDriverWait w =new WebDriverWait(driver,30);
			
		String name= new String();
		String size= new String();
				
		driver.get("https://www.myntra.com/login?");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		
	//send phone number
		driver.findElement(By.xpath(("//div[@class='signInContainer']/div[2]/div/input"))).sendKeys("phone number");
		driver.findElement(By.cssSelector("div.submitBottomOption")).click();
		w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='bottomeLink']/span")));
		driver.findElement(By.xpath("//div[@class='bottomeLink']/span")).click();
	//send password
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("password");
		driver.findElement(By.cssSelector("button.btn.primary.lg.block.submitButton")).click();
		Thread.sleep(2000);
	
	//open wishlist
		driver.findElement(By.cssSelector("span.myntraweb-sprite.desktop-iconWishlist.sprites-headerWishlist")).click();
		Thread.sleep(4000);
		
	//scroll till bottom to dynamically load all products with vertical scroll	
		Object lastHeight = ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");
        while (true) 
        {	((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
		    Thread.sleep(2000);
		    Object newHeight = ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");
		    if (newHeight.equals(lastHeight))		            	
		    break;
		    lastHeight = newHeight;
        }

	//add out of stock items to list
		List<WebElement> outofstock = driver.findElements(By.xpath("//img[@class='itemcard-outOfStockItemImage itemcard-itemImage']/parent::picture/parent::a/parent::div/parent::div/div[2]/div/p[1]"));
	//print out of stock items	
		//System.out.println(outofstock.size()+"PRODUCTS ARE OUT OF STOCK");
		//for (WebElement product: outofstock)
		//System.out.println(product.getText());
	
	//add available items to list
		List<WebElement> available = driver.findElements(By.xpath("//img[@class='itemcard-itemImage']/parent::picture/parent::a/parent::div/parent::div/div[2]/div/p[1]"));
	//print available items	
		//System.out.println(available.size()+"PRODUCTS ARE AVAILABLE");
		//for (WebElement product: available)
		//System.out.println(product.getText());
	
	//add required items to be added to cart
		List<List<String>> cartlist = new ArrayList<List<String>>();
		cartlist.add(Arrays.asList("Roadster Women Brown & Black Boxy Check Shirt with Print","S"));
	    cartlist.add(Arrays.asList("H&M Women White Linen-Blend Shirt","8"));
	    cartlist.add(Arrays.asList("Global Desi Women Mustard Yellow & Turquoise Blue Printed Gathered Detail A-Line Dress","L"));
	    cartlist.add(Arrays.asList("H&M Women Beige Fine-Knit Polo-Neck Jumper","M"));
	    cartlist.add(Arrays.asList("Varanga Women Black & Golden Printed Crop Top","M"));
	    cartlist.add(Arrays.asList("Taavi Women Grey & Off-White Block Printed Heeled Mules","40"));
	    cartlist.add(Arrays.asList("Ginger by Lifestyle Women Pink & White Striped Empire Top","XS"));
	    cartlist.add(Arrays.asList("Eavan Women Pink Self Design Top","S"));
	    cartlist.add(Arrays.asList("CODE by Lifestyle Women Mustard Yellow Solid Mules","S"));
	    	    
	//list of all web elements of 'move to bag'
	    List<WebElement> bag = driver.findElements(By.xpath("//p[@class='itemdetails-itemDetailsLabel']/parent::div/parent::div/div[2]/span/a"));
	    
	//add to bag
	   for(int i=0; i<cartlist.size(); i++)
	    {	int productflag=0;
	    	name = cartlist.get(i).get(0);
	    	size = cartlist.get(i).get(1);
	    	for(WebElement j:available)
	    	{	if(j.getText().equals(name))
	    		{	productflag=1;
	    			
	    			//scroll and locate element   		
	    			Thread.sleep(2000);
	    			Actions actions = new Actions(driver);
	    			actions.sendKeys(Keys.HOME).build().perform();
	    			Thread.sleep(4000);
	    			JavascriptExecutor je = (JavascriptExecutor) driver;
						while(!(j.isDisplayed()))
						{	je.executeScript("arguments[0].scrollIntoView(true);",j);
							Thread.sleep(3000);
						}
				
					//Move to bag
						int index=available.indexOf(j);
						bag.get(index).click();
	    		 		 				
	    			//add available sizes to a list
	    				List<WebElement> availablesizes = driver.findElements(By.xpath("//div[@class='sizeselect-sizeButtonsContaier']/button"));
	    				int sizeflag=0;
	    			//choose required size from the available sizes
	    				for(WebElement s:availablesizes)
	    				{	if(s.getText().equalsIgnoreCase(size))
	      					{	//size available
	    							s.click();
	    							Thread.sleep(1000);
	      						//Click done
	    							driver.findElement(By.xpath("//div[@class='sizeselect-done']")).click();
	      					   		System.out.println(name+" of size "+size+"added to bag");
	      					   		sizeflag=1;
	      					   		bag.remove(index);
	      					   		available.remove(index);
	      					   		Thread.sleep(4000);
	      					   		break;
	      					}
	    				}
	    				if(sizeflag==0)
	    				{	driver.findElement(By.xpath("//span[@class='myntraweb-sprite sizeselect-sizeDisplayRemoveMark sprites-remove']")).click();
	    					System.out.println(size+" not available in "+name);
	    					Thread.sleep(3000);
	    					break;
	    				}
	    				
	    				break;	    			
	    		}
	       	}
	    	 if(productflag==0)
	 			System.out.println(name+" not available");

	    }
	  
	   
	   Thread.sleep(2000);
	   driver.findElement(By.xpath("//span[@class='myntraweb-sprite desktop-iconBag sprites-headerBag']")).click();
		
	}

}
