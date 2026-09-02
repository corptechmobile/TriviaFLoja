package br.com.webapp.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;

import org.primefaces.model.CroppedImage;

@ManagedBean(name = "imageCropperBean")
@ViewScoped
public class ImageCropperBean implements Serializable {
	
	private static final long serialVersionUID = 6128278130355698308L;

	public ImageCropperBean(){
	}
	
	private CroppedImage croppedImage;  
    private String newImageName;  
    
  
    public String crop2(){
    	
    	System.out.println("[ImageCropperBean]crop2");
    	
		return null;
    }
    
    public String crop() {  
    	
    	System.out.println("[ImageCropperBean]crop");
    	
        if(croppedImage == null)  
            return null;  
          
        setNewImageName(getRandomImageName());  
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();  
        String newFileName = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "uploadcorp" + File.separator + getNewImageName() + ".jpg";
        
        System.out.println("newFileName: " + newFileName);
          
        FileImageOutputStream imageOutput;  
        try {  
            imageOutput = new FileImageOutputStream(new File(newFileName));  
            imageOutput.write(croppedImage.getBytes(), 0, croppedImage.getBytes().length);  
            imageOutput.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (Exception e) {
        	e.printStackTrace();
		}
          
        return null;  
    } 
    
    private String getRandomImageName() {  
        int i = (int) (Math.random() * 100000);  
          
        return String.valueOf(i);  
    }
    
    // gets e sets    
    
    public CroppedImage getCroppedImage() {  
        return croppedImage;  
    }  
  
    public void setCroppedImage(CroppedImage croppedImage) {  
        this.croppedImage = croppedImage;  
    } 
      
    public String getNewImageName() {  
        return newImageName;  
    }  
  
    public void setNewImageName(String newImageName) {  
        this.newImageName = newImageName;  
    }
    
    

}
