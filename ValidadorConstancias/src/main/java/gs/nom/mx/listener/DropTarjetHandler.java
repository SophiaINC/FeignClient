/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gs.nom.mx.listener;

import gs.nom.mx.views.Principal;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import javax.swing.JTextArea;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 *
 * @author acruzb
 */
public class DropTarjetHandler implements DropTargetListener {

    private static final Logger LOGGER = Logger.getLogger(DropTarjetHandler.class);
    private Principal principal;

    public DropTarjetHandler(Principal principal) {
        this.principal = principal;
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        Transferable transferable = dtde.getTransferable();
        if (dtde.isDataFlavorSupported(DataFlavor.imageFlavor.javaFileListFlavor)) {
            dtde.acceptDrop(dtde.getDropAction());
            try {

                List transferData = (List) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                if (transferData != null && !transferData.isEmpty() && transferData.size() == 1) {
                    LOGGER.info("Se copia el archivo usando modo drag and drop");
                    LOGGER.info(transferData);

                    String fileName = transferData.get(0).toString();
                    File file = new File(fileName);
                    FileInputStream fileInputStreamReader;
                    try {
                        fileInputStreamReader = new FileInputStream(file);
                        byte[] bytes = new byte[(int) file.length()];
                        fileInputStreamReader.read(bytes);
//                        principal.setFileBase64(new String(Base64.encodeBase64(bytes)));
                    } catch (IOException ex) {
                        LOGGER.info("Ocurrio un error al abtener el base 64 del archivo: " + fileName, ex);
                    }
                    dtde.dropComplete(true);
                } else if (transferData.size() > 1) {
//                    Principal.mostrarMensajeVista("Solo se permite arrastrar un archivo.");
                    dtde.dropComplete(true);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            dtde.rejectDrop();
        }
    }

}
