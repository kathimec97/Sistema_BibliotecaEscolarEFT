package cl.duoc.modelo;


/**
 * Representa un libro físico dentro del inventario de la biblioteca.
 * Contiene la información bibliográfica y el stock disponible para préstamos.
 *
 * @author Katherine
 */
public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private String isbn;
    private String editorial;
    private int stock;
    private Categoria id_categoria;


    public Libro(int id, String titulo, String autor, String isbn, String editorial, int stock, Categoria id_categoria) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.editorial = editorial;
        this.stock = stock;
        this.id_categoria = id_categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Categoria getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Categoria id_categoria) {
        this.id_categoria = id_categoria;
    }

    @Override
    public String toString() {
        return "Libros: " + '\n' +
                "ID: " + id + '\n' +
                "Titulo: " + titulo + '\n' +
                "Autor: " + autor + '\n' +
                "ISBN: " + isbn + '\n' +
                "Editorial: " + editorial + '\n' +
                "Stock: " + stock + '\n' +
                "ID categoria: " + id_categoria;
    }
}
