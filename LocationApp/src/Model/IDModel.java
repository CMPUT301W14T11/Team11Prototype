package Model;

public class IDModel {
       private int id_for_master;
       //private int id_for_sub_comment;
       // constructor for id object
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
