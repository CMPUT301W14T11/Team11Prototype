package Model;
/**
 * Use this class to construct a IDModel
 * @author zuo2
 */
public class IDModel {
       private int id_for_master;
       /**
        * constuctor to make a IDModel object
        * @param idMaster
        */
       public IDModel(int idMaster)
       {
    	   this.id_for_master=idMaster;
       }
       /**
        * to get the master id
        * @return
        */
       public int getId_for_master() {
    	   return id_for_master;
       }
	   /**
	    * to set the master id by given integer
	    * @param id_for_master
	    */
       public void setId_for_master(int id_for_master) {
    	   this.id_for_master = id_for_master;
       }

     
}
