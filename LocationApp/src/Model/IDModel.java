package Model;
/**
 * Use this class to construct a IDModel
 * @author yazhou
 *
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
	public int getId_for_master() {
		return id_for_master;
	}
	
	public void setId_for_master(int id_for_master) {
		this.id_for_master = id_for_master;
	}

     
}
