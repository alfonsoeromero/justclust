/* <![CDATA[ */
// ==============================================================================
// ==============================================================================
//									SLIDE SHOW
//
//
//
//ADAPTED FROM SEVERAL SOURCES ONLINE
//
/*Small slideshow in javascript
Copyright (C) 2012  Horacio Caniza

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.*/
//
//
//
// ==============================================================================
// ==============================================================================
/*
 * Gene Image: G_*_*.jpg: Source Wikipedia
 * Protein image: P_*_*.jpg: Licence GPLv2
 * Other images: PaccanaroLab property.
 */


// Set slideShowSpeed (milliseconds)
var slideShowSpeed = 2000
// Duration of crossfade (seconds)
var crossFadeDuration = 3
var t;
//the number of images shown in the website
var numImageSpots = 3;

//to simplify the code: maxMilliSec is actually the
//desired maxMilliSec minus minMilliSec. 
//So, if i want a max duration of 3000 and a min duration of
//1000 i choose 2000.
var maxMilliSec = 20;
var minMilliSec = 1000;


//class to handle the images..the current
//way is making my eyes bleed..
function ImageRes(){
	var generic_id; //stores a generic id por each image. 
			//independent of the size of the image.
			//so it doesn't repeat. 
	var size; //all images have the same height, so
		  //it's only necesarry to specify the X
		  //size.
	var displayed; //simply a bool to check if it's beeing
			//displayed already
	var path; //stores the path of the imagea
	var img_name; //name of the image
	var preloaded_img; //preloaded img
	//get image name
	this.getName = function()	{
		return this.img_name;
	}
	//check image as used
	this.setName = function(new_name){
		this.img_name = new_name;
	}
	//get image used
	this.getDisplayed = function()	{
		return this.displayed;
	}
	//check image as used
	this.setDisplayed = function(new_displayed){
		this.displayed = new_displayed;
	}
	//set the generic id
	this.setGenericID = function(new_gen_id){
		this.generic_id = new_gen_id;
	}
	//get generic id
	this.getGenericID = function(){
		return this.generic_id;
	}
	//set size
	this.setSize = function(new_size){
		this.size = new_size;
	}
	//get size
	this.getSize = function(){
		return this.size;
	}
	//set path
	this.setPath = function(new_path){
		this.path = new_path;
		this.preloaded_img  = new Image();
		this.preloaded_img = new_path;
	}
	//get path
	this.getPath = function(){
		return this.path;
	}
	//get preloaded_img
	this.getPreloadedImg = function(){
		return this.preloaded_img;
	}

}

var numImagesPreloaded220 = 12;
var numImagesPreloaded150 = 8;
var numImagesPreloaded230 = 8;

//we need three individual image arrays, simply because
//the slots are different. The images must, therefore, be
//of different size.
var Images = new Array(3); 
Images[0] = new Array(numImagesPreloaded220);
Images[1] = new Array(numImagesPreloaded150);
Images[2] = new Array(numImagesPreloaded230);


//var path = "/";
var path = "http://www.paccanarolab.org/";

var i = 0;
Images[0][i] = new ImageRes();
Images[0][i].setDisplayed(false);
Images[0][i].setGenericID("S_1");
Images[0][i].setSize(220);
Images[0][i].setPath(path+'wp-content/uploads/2012/01/clock_1_220_191.jpg');
i = i + 1;
Images[0][i] = new ImageRes();
Images[0][i].setDisplayed(false);
Images[0][i].setGenericID("S_2");
Images[0][i].setSize(220);
Images[0][i].setPath(path+'wp-content/uploads/2012/01/clock_2_220_191.jpg');
i = i + 1;
Images[0][i] = new ImageRes();
Images[0][i].setDisplayed(false);
Images[0][i].setGenericID("S_3");
Images[0][i].setSize(220);
Images[0][i].setPath(path+'wp-content/uploads/2012/01/clock_3_220_191.jpg');
i = i + 1;
Images[0][i] = new ImageRes();
Images[0][i].setDisplayed(false);
Images[0][i].setGenericID("Sa_4");
Images[0][i].setSize(220);
Images[0][i].setPath(path+'wp-content/uploads/2012/01/clock_4_220_191.jpg');
i = i + 1;
Images[0][i] = new ImageRes();
Images[0][i].setDisplayed(false);
Images[0][i].setGenericID("Sa_5");
Images[0][i].setSize(220);
Images[0][i].setPath(path+'wp-content/uploads/2012/01/clock_5_220_191.jpg');
i = i + 1;
Images[0][i] = new ImageRes();
Images[0][i].setDisplayed(false);
Images[0][i].setGenericID("Sa_6");
Images[0][i].setSize(220);
Images[0][i].setPath(path+'wp-content/uploads/2012/01/clock_6_220_191.jpg');
i = i + 1;
Images[0][i] = new ImageRes();
Images[0][i].setDisplayed(false);
Images[0][i].setGenericID("Sa_7");
Images[0][i].setSize(220);
Images[0][i].setPath(path+'wp-content/uploads/2012/01/clock_7_220_191.jpg');
i = i + 1;
Images[0][i] = new ImageRes();
Images[0][i].setDisplayed(false);
Images[0][i].setGenericID("H_1");
Images[0][i].setSize(220);
Images[0][i].setPath(path+'wp-content/uploads/2012/01/H_1_220_191.jpg');
i = i + 1;
Images[0][i] = new ImageRes();
Images[0][i].setDisplayed(true);
Images[0][i].setGenericID("H_2");
Images[0][i].setSize(220);
Images[0][i].setPath(path+'wp-content/uploads/2012/01/H_2_220_191.jpg');
i = i + 1;
Images[0][i] = new ImageRes();
Images[0][i].setDisplayed(false);
Images[0][i].setGenericID("H_3");
Images[0][i].setSize(220);
Images[0][i].setPath(path+'wp-content/uploads/2012/01/H_3_220_191.jpg');
i = i + 1;
Images[0][i] = new ImageRes();
Images[0][i].setDisplayed(false);
Images[0][i].setGenericID("H_4");
Images[0][i].setSize(220);
Images[0][i].setPath(path+'wp-content/uploads/2012/01/H_4_220_191.jpg');
i = i + 1;
Images[0][i] = new ImageRes();
Images[0][i].setDisplayed(false);
Images[0][i].setGenericID("OLD_1");
Images[0][i].setSize(220);
Images[0][i].setPath(path+'wp-content/uploads/2012/01/OLD_1_220_191.jpg');
i = i + 1;
Images[0][i] = new ImageRes();
Images[0][i].setDisplayed(false);
Images[0][i].setGenericID("OLD_2");
Images[0][i].setSize(220);
Images[0][i].setPath(path+'wp-content/uploads/2012/01/OLD_2_220_191.jpg');
i = i + 1;
Images[0][i] = new ImageRes();
Images[0][i].setDisplayed(false);
Images[0][i].setGenericID("GC_1");
Images[0][i].setSize(220);
Images[0][i].setPath(path+'wp-content/uploads/2012/01/GC_1_220_191.jpg');
i = i + 1;
Images[0][i] = new ImageRes();
Images[0][i].setDisplayed(false);
Images[0][i].setGenericID("GC_2");
Images[0][i].setSize(220);
Images[0][i].setPath(path+'wp-content/uploads/2012/01/GC_2_220_191.jpg');
i = i + 1;
Images[0][i] = new ImageRes();
Images[0][i].setDisplayed(false);
Images[0][i].setGenericID("GC_3");
Images[0][i].setSize(220);
Images[0][i].setPath(path+'wp-content/uploads/2012/01/GC_3_220_191.jpg');
i = i + 1;
Images[0][i] = new ImageRes();
Images[0][i].setDisplayed(false);
Images[0][i].setGenericID("SS_1");
Images[0][i].setSize(220);
Images[0][i].setPath(path+'wp-content/uploads/2012/01/SS_1_220_191.jpg');
i = i + 1;
Images[0][i] = new ImageRes();
Images[0][i].setDisplayed(false);
Images[0][i].setGenericID("SS_2");
Images[0][i].setSize(220);
Images[0][i].setPath(path+'wp-content/uploads/2012/01/SS_2_220_191.jpg');
i = i + 1;
Images[0][i] = new ImageRes();
Images[0][i].setDisplayed(false);
Images[0][i].setGenericID("PR_1");
Images[0][i].setSize(220);
Images[0][i].setPath(path+'wp-content/uploads/2012/01/PR_1_220_191.jpg');
i = i + 1;
Images[0][i] = new ImageRes();
Images[0][i].setDisplayed(false);
Images[0][i].setGenericID("PR_2");
Images[0][i].setSize(220);
Images[0][i].setPath(path+'wp-content/uploads/2012/01/PR_2_220_191.jpg');
//150 pictures
i = 0;
Images[1][i] = new ImageRes();
Images[1][i].setDisplayed(false);
Images[1][i].setGenericID("Sa_2");
Images[1][i].setSize(150);
Images[1][i].setPath(path+'wp-content/uploads/2012/01/clock_2_150_191.jpg');
i = i + 1;
Images[1][i] = new ImageRes();
Images[1][i].setDisplayed(false);
Images[1][i].setGenericID("Sa_4");
Images[1][i].setSize(150);
Images[1][i].setPath(path+'wp-content/uploads/2012/01/clock_4_150_191.jpg');
i = i + 1;
Images[1][i] = new ImageRes();
Images[1][i].setDisplayed(false);
Images[1][i].setGenericID("Sa_5");
Images[1][i].setSize(150);
Images[1][i].setPath(path+'wp-content/uploads/2012/01/clock_5_150_191.jpg');
i = i + 1;
Images[1][i] = new ImageRes();
Images[1][i].setDisplayed(false);
Images[1][i].setGenericID("Sa_7");
Images[1][i].setSize(150);
Images[1][i].setPath(path+'wp-content/uploads/2012/01/clock_7_150_191.jpg');
i = i + 1;
Images[1][i] = new ImageRes();
Images[1][i].setDisplayed(false);
Images[1][i].setGenericID("H_3");
Images[1][i].setSize(150);
Images[1][i].setPath(path+'wp-content/uploads/2012/01/H_3_150_191.jpg');
i = i + 1;
Images[1][i] = new ImageRes();
Images[1][i].setDisplayed(false);
Images[1][i].setGenericID("GC_1");
Images[1][i].setSize(150);
Images[1][i].setPath(path+'wp-content/uploads/2012/01/GC_1_150_191.jpg');
i = i + 1;
Images[1][i] = new ImageRes();
Images[1][i].setDisplayed(false);
Images[1][i].setGenericID("GC_2");
Images[1][i].setSize(150);
Images[1][i].setPath(path+'wp-content/uploads/2012/01/GC_2_150_191.jpg');
i = i + 1;
Images[1][i] = new ImageRes();
Images[1][i].setDisplayed(false);
Images[1][i].setGenericID("GC_3");
Images[1][i].setSize(150);
Images[1][i].setPath(path+'wp-content/uploads/2012/01/GC_3_150_191.jpg');
i = i + 1;
Images[1][i] = new ImageRes();
Images[1][i].setDisplayed(true);
Images[1][i].setGenericID("SS_1");
Images[1][i].setSize(150);
Images[1][i].setPath(path+'wp-content/uploads/2012/01/SS_1_150_191.jpg');
i = i + 1;
Images[1][i] = new ImageRes();
Images[1][i].setDisplayed(false);
Images[1][i].setGenericID("OLD_1");
Images[1][i].setSize(150);
Images[1][i].setPath(path+'wp-content/uploads/2012/01/OLD_1_150_191.jpg');
i = i + 1;
Images[1][i] = new ImageRes();
Images[1][i].setDisplayed(false);
Images[1][i].setGenericID("OLD_2");
Images[1][i].setSize(150);
Images[1][i].setPath(path+'wp-content/uploads/2012/01/OLD_2_150_191.jpg');
i = i + 1;
Images[1][i] = new ImageRes();
Images[1][i].setDisplayed(false);
Images[1][i].setGenericID("PR_1");
Images[1][i].setSize(150);
Images[1][i].setPath(path+'wp-content/uploads/2012/01/PR_1_150_191.jpg');
i = i + 1;
Images[1][i] = new ImageRes();
Images[1][i].setDisplayed(false);
Images[1][i].setGenericID("PR_2");
Images[1][i].setSize(150);
Images[1][i].setPath(path+'wp-content/uploads/2012/01/PR_2_150_191.jpg');
//230 pics
i = 0;
Images[2][i] = new ImageRes();
Images[2][i].setDisplayed(false);
Images[2][i].setGenericID("Sa_1");
Images[2][i].setSize(230);
Images[2][i].setPath(path+'wp-content/uploads/2012/01/clock_1_230_191.jpg');
i = i + 1;
Images[2][i] = new ImageRes();
Images[2][i].setDisplayed(false);
Images[2][i].setGenericID("Sa_2");
Images[2][i].setSize(230);
Images[2][i].setPath(path+'wp-content/uploads/2012/01/clock_2_230_191.jpg');
i = i + 1;
Images[2][i] = new ImageRes();
Images[2][i].setDisplayed(false);
Images[2][i].setGenericID("Sa_3");
Images[2][i].setSize(230);
Images[2][i].setPath(path+'wp-content/uploads/2012/01/clock_3_230_191.jpg');
i = i + 1;
Images[2][i] = new ImageRes();
Images[2][i].setDisplayed(false);
Images[2][i].setGenericID("Sa_4");
Images[2][i].setSize(230);
Images[2][i].setPath(path+'wp-content/uploads/2012/01/clock_4_230_191.jpg');
i = i + 1;
Images[2][i] = new ImageRes();
Images[2][i].setDisplayed(false);
Images[2][i].setGenericID("Sa_5");
Images[2][i].setSize(230);
Images[2][i].setPath(path+'wp-content/uploads/2012/01/clock_5_230_191.jpg');
i = i + 1;
Images[2][i] = new ImageRes();
Images[2][i].setDisplayed(false);
Images[2][i].setGenericID("Sa_6");
Images[2][i].setSize(230);
Images[2][i].setPath(path+'wp-content/uploads/2012/01/clock_6_230_191.jpg');
i = i + 1;
Images[2][i] = new ImageRes();
Images[2][i].setDisplayed(false);
Images[2][i].setGenericID("Sa_7");
Images[2][i].setSize(230);
Images[2][i].setPath(path+'wp-content/uploads/2012/01/clock_7_230_191.jpg');
i = i + 1;
Images[2][i] = new ImageRes();
Images[2][i].setDisplayed(false);
Images[2][i].setGenericID("H_3");
Images[2][i].setSize(230);
Images[2][i].setPath(path+'wp-content/uploads/2012/01/H_3_230_191.jpg');
i = i + 1;
Images[2][i] = new ImageRes();
Images[2][i].setDisplayed(true);
Images[2][i].setGenericID("H_4");
Images[2][i].setSize(230);
Images[2][i].setPath(path+'wp-content/uploads/2012/01/H_4_230_191.jpg');
i = i + 1;
Images[2][i] = new ImageRes();
Images[2][i].setDisplayed(false);
Images[2][i].setGenericID("GC_1");
Images[2][i].setSize(230);
Images[2][i].setPath(path+'wp-content/uploads/2012/01/GC_1_230_191.jpg');
i = i + 1;
Images[2][i] = new ImageRes();
Images[2][i].setDisplayed(false);
Images[2][i].setGenericID("GC_2");
Images[2][i].setSize(230);
Images[2][i].setPath(path+'wp-content/uploads/2012/01/GC_2_230_191.jpg');
i = i + 1;
Images[2][i] = new ImageRes();
Images[2][i].setDisplayed(false);
Images[2][i].setGenericID("GC_3");
Images[2][i].setSize(230);
Images[2][i].setPath(path+'wp-content/uploads/2012/01/GC_3_230_191.jpg');
i = i + 1;
Images[2][i] = new ImageRes();
Images[2][i].setDisplayed(false);
Images[2][i].setGenericID("SS_2");
Images[2][i].setSize(230);
Images[2][i].setPath(path+'wp-content/uploads/2012/01/SS_2_230_191.jpg');
i = i + 1;
Images[2][i] = new ImageRes();
Images[2][i].setDisplayed(false);
Images[2][i].setGenericID("PR_1");
Images[2][i].setSize(230);
Images[2][i].setPath(path+'wp-content/uploads/2012/01/PR_1_230_191.jpg');
i = i + 1;
Images[2][i] = new ImageRes();
Images[2][i].setDisplayed(false);
Images[2][i].setGenericID("PR_2");
Images[2][i].setSize(230);
Images[2][i].setPath(path+'wp-content/uploads/2012/01/PR_2_230_191.jpg');
i = i + 1;
Images[2][i] = new ImageRes();
Images[2][i].setDisplayed(false);
Images[2][i].setGenericID("OLD_1");
Images[2][i].setSize(230);
Images[2][i].setPath(path+'wp-content/uploads/2012/01/OLD_1_230_191.jpg');
i = i + 1;
Images[2][i] = new ImageRes();
Images[2][i].setDisplayed(false);
Images[2][i].setGenericID("OLD_2");
Images[2][i].setSize(230);
Images[2][i].setPath(path+'wp-content/uploads/2012/01/OLD_2_230_191.jpg');





function runSlideShow(){

  //choose the actual preloaded picture that is going to get loaded.
  //no two adjacent images are equal 
  var j = 0; //image index 
  var imgIndex = 0; //image slot index.
  //init "static" variable that is going to check last changed variable.
  if ( typeof runSlideShow.lastImgChosen == 'undefined' ) {
	      runSlideShow.lastImgChosen = -1;
  }
  //choose the picture that is going to change randomly.
  while(true)
  {
    imgIndex = Math.floor(Math.random()*(numImageSpots)); 
    //if the last image we've changed is the same, change it again
    if(runSlideShow.lastImgChosen != imgIndex)
    {
	runSlideShow.lastImgChosen = imgIndex;
	break;
    }
  }
  //choose the actual picture to be shown. If some of the
  //pictures showing are the same, just change it.
  //this, sadly, doesn't apply anymore. The problem is that 
  //not all slots are pooled from the same set of images, so
  //we should choose the images independently. We could have repetition, 
  //but..the code remains, but it is commented out.  
  //now, you get an index. Since we've stored the elements in sequential order
  var range = findCorrectRange(imgIndex);
  while(true)
  {    
      //choose the image to show
      j =  Math.floor(Math.random() * range);
      //get the generic_id for the current image chosen.
      //cicle through all other images with the same generic id and check if it is displayed.
      if(checkDuplication(Images[imgIndex][j].getGenericID()))
	      continue;
      break;	
  }
  //----

 //change the picture chosen

  displayImageChosen(imgIndex, j);

  t = setTimeout('runSlideShow()', slideShowSpeed);
  
}


function findCorrectRange(imgIndex)
{
  switch  (imgIndex) {
   	case 0:
		return numImagesPreloaded220;
	case 1:
		return numImagesPreloaded150;
	case 2: 
		return numImagesPreloaded230;
	}
}


function displayImageChosen(imgIndex, j){
  //choose the number of milliseconds the chosen image is going to last
  var millisec = Math.floor(Math.random()*maxMilliSec) + minMilliSec;
  switch  (imgIndex) {
   	case 0:
		//choose image, check all others as not used.
		for(i = 0; i < numImagesPreloaded220; i++)
			Images[imgIndex][i].setDisplayed(false);
		//check the current image as used.
		Images[imgIndex][j].setDisplayed(true);

		blendImage('blenddiv1','blendimage1', Images[0][j].getPreloadedImg(),millisec);
		break; 
	case 1:
		//choose image, check all others as not used.
		for(i = 0; i < numImagesPreloaded150; i++)
			Images[imgIndex][i].setDisplayed(false);
		//check the current image as used.
		Images[imgIndex][j].setDisplayed(true);

		blendImage('blenddiv2','blendimage2', Images[1][j].getPreloadedImg(),millisec);
		break;
	case 2: 
		//choose image, check all others as not used.
		for(i = 0; i < numImagesPreloaded230; i++)
			Images[imgIndex][i].setDisplayed(false);
		//check the current image as used.
		Images[imgIndex][j].setDisplayed(true);

		blendImage('blenddiv3','blendimage3', Images[2][j].getPreloadedImg(),millisec);
		break;

	}
}

function checkDuplication(current_id)
{
      for(i = 0; i < numImagesPreloaded220; i++){
	  if (Images[0][i].getGenericID() == current_id)
	  {
		  if(Images[0][i].getDisplayed() == true)
			  return true;
		  else 
			  break;
	  }
      }
      for(i = 0; i < numImagesPreloaded150; i++){
	  if (Images[1][i].getGenericID() == current_id)
	  {
		  if(Images[1][i].getDisplayed() == true)
			  return true;
		  else
			  break;
	  }
      }
      for(i = 0; i < numImagesPreloaded230; i++){
	  if (Images[2][i].getGenericID() == current_id)
	  {
		  if(Images[2][i].getDisplayed() == true)
			  return true;
		  else 
			  break;
	  }
      }
      return false;
}


function blendImage(divid, imageid, imagefile, millisec) { 
    var speed = Math.round(millisec / 1000); 
    var timer = 0; 
     document.getElementById(divid).style.backgroundImage = "url(" + document.getElementById(imageid).src + ")"; 
     changeOpac(0, imageid);
     document.getElementById(imageid).src = imagefile;

    for(i = 70; i <= 100; i=i+2) { 
        setTimeout("changeOpac(" + i + ",'" + imageid + "')",(timer * speed)); 
        timer++; 
    } 
} 

function changeOpac(opacity, id) { 	
    var object = document.getElementById(id).style; 
	if (opacity==0) 
		object.visibility='hidden'
	else
	    object.visibility='visible';
    object.filter = "alpha(opacity=" + opacity + ")";     
    object.opacity = (opacity / 100); 
} 

// ==============================================================================
// ==============================================================================
//								END OF SLIDE SHOW
// ==============================================================================
// ==============================================================================
/* ]]> */
