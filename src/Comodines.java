public class Comodines {
  
    public boolean disponible5050 = true;
    public boolean disponibleChat = true;
    public boolean disponibleLlamada = true;
    public boolean disponibleMago = true;
    public boolean disponibleSacrificio = true;

  
    public int usarRuleta() {
 
        int resultado = (int) (Math.random() * 4);
        return resultado;
    }
    
   
    public void usarMago() {
        this.disponibleMago = false;
    }
}