package sample;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.*;

public class Watcher implements Runnable {

    private String ruta;
    private String rutaProcesados;
    private String rutaNoAplicados;

    //WatchService service = null;
    //WatchKey watchKey = null;

    public Watcher(String ruta) {
        this.ruta = ruta;
        this.rutaProcesados = ruta + "/Processed";
        this.rutaNoAplicados = ruta + "/Not applicable";
    }

    @Override
    public void run() {
        try {
            WatchService service = FileSystems.getDefault().newWatchService();
            Path directory = Paths.get(ruta);

            crearCarpetas();

            WatchKey watchKey = directory.register(service,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE);
            while (true){
                for (WatchEvent<?> event : watchKey.pollEvents()){
                    Path file = directory.resolve((Path) event.context());
                    String nombreArchivo = file.toFile().getName();
                    String rutaArchivo = file.toFile().getAbsolutePath();
                    System.out.println(event.kind().name());
                    if (event.kind().name().equals("ENTRY_CREATE")){
                        if (FilenameUtils.getExtension(nombreArchivo).startsWith("xls")){
                            Excel excel = new Excel(rutaArchivo, rutaProcesados, nombreArchivo);
                            excel.getLibro();
                            file.toFile().renameTo(new File(rutaProcesados + "/" + nombreArchivo));
                            // enviar ruta del archivo
                        }else{
                            file.toFile().renameTo(new File(rutaNoAplicados + "/" + nombreArchivo));
                        }
                    }
                    //System.out.println(event.kind() + " - " + file.toFile().getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void crearCarpetas(){
        File fCrear = new File(rutaProcesados);
        fCrear.mkdir();
        fCrear.renameTo(new File(rutaNoAplicados));
        fCrear.mkdir();
    }
}
