package vista;


public interface IFormulario<T> {

	public void modificarDatos(T dato) throws Exception;;
	public void borrarDatos	(T dato) throws Exception;
	public void crearDatos(T dato)  throws Exception;
	public void buscarDatos(T dato)  throws Exception;

}
