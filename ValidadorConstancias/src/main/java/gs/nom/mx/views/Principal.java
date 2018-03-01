/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gs.nom.mx.views;

import gs.nom.mx.concurrent.ThreatChecarProceso;
import gs.nom.mx.enums.LOADING_MODE;
import gs.nom.mx.listener.DropTarjetHandler;
import gs.nom.mx.listener.PrincipalEventsAdapter;
import gs.nom.mx.util.FileUtils;
import gs.nom.mx.ws.ValidadorNOMImp;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.dnd.DropTarget;
import java.io.IOException;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author acruzb
 */
public class Principal extends javax.swing.JFrame {

    private static final Logger LOGGER = Logger.getLogger(Principal.class);

    public static String VERSION;
    public static ApplicationContext applicationContext;

    public static DialogLoading dialogLoading;

    //Estilo
    private static StyledDocument contenidoDoc;
    private final static String MIDDLELINE = "                                                                                                                           ";
    private final static String STARTLINE = "                                                           INICIO                                                          ";
    private final static String ENDLINE = "                                                            FIN                                                            ";
    private final static String FONT_FAMILY = "Monospaced";
    public static SimpleAttributeSet keyWordStyleAzul;
    public static SimpleAttributeSet keyWordStyleRojo;
    public static SimpleAttributeSet keyWordStyle;

    public String archivoBase64 = "";
    public String archivoTipo = "pdf";
    
    private JPanel panelAprobado;
    private javax.swing.JScrollPane scrollResultado;
    private javax.swing.JTextPane textPaneSalida;

    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
        init();
        //Se centra la ventana
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("images/checkOK.png")).getImage());
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setTitle("Validadore de constancias " + VERSION);

        contenidoDoc = textPaneSalida.getStyledDocument();
        keyWordStyleAzul = new SimpleAttributeSet();
        StyleConstants.setForeground(keyWordStyleAzul, Color.BLUE);
        StyleConstants.setBold(keyWordStyleAzul, true);
        StyleConstants.setFontFamily(keyWordStyleAzul, FONT_FAMILY);
        StyleConstants.setFontSize(keyWordStyleAzul, 13);
        
        keyWordStyleRojo = new SimpleAttributeSet();
        StyleConstants.setForeground(keyWordStyleRojo, Color.RED);
        StyleConstants.setBold(keyWordStyleRojo, true);
        StyleConstants.setFontFamily(keyWordStyleRojo, FONT_FAMILY);
        StyleConstants.setFontSize(keyWordStyleRojo, 13);

        keyWordStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(keyWordStyle, Color.BLACK);
        StyleConstants.setBackground(keyWordStyle, new Color(241, 241, 241));
        StyleConstants.setBold(keyWordStyle, true);
        StyleConstants.setFontFamily(keyWordStyle, FONT_FAMILY);
        StyleConstants.setFontSize(keyWordStyle, 13);
    }

    private void init() {
        scrollResultado = new javax.swing.JScrollPane();
        textPaneSalida = new javax.swing.JTextPane();
        textPaneSalida.setEditable(false);
        scrollResultado.setViewportView(textPaneSalida);
        scrollResultado.setSize(layerPanePrincipal.getSize());
        layerPanePrincipal.add(scrollResultado, 2);
        
        
        DropTarget dt = new DropTarget();
        try {//Se agrega el listener
            dt.addDropTargetListener(new DropTarjetHandler(this));
            textFieldURLArchivo.setDropTarget(dt);
        } catch (TooManyListenersException ex) {
        }
        
        dialogLoading = new DialogLoading(this);
        dialogLoading.setLocationRelativeTo(this);
        dialogLoading.getRootPane().setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        this.addComponentListener(new PrincipalEventsAdapter(this, dialogLoading));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        panelBusqueda = new javax.swing.JPanel();
        btnValidar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textFieldIdentificador = new javax.swing.JTextField();
        textFieldURLArchivo = new javax.swing.JTextField();
        btnLimpiar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        textFieldConstancia = new javax.swing.JTextField();
        labelStatus = new javax.swing.JLabel();
        layerPanePrincipal = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        checkBoxMenuProxy = new javax.swing.JCheckBoxMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelPrincipal.setLayout(new java.awt.BorderLayout());

        panelBusqueda.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnValidar.setText("Validar");
        btnValidar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidarActionPerformed(evt);
            }
        });

        jLabel2.setText("URL/Archivo");

        jLabel3.setText("Identificador");

        textFieldURLArchivo.setText("http://200.38.122.86/img1/BazDigital/Pdfs/2018/01/18/9215dcfcd0274de2ba05a88893dc21b3/4/55/5-20180118173830.pdf");

        btnLimpiar.setText("Limpiar campos");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        jLabel4.setText("Constancia");

        textFieldConstancia.setText("http://10.63.100.185/img11/digitalizacion_web/2018/01/18/FOTOYAUDIO/9215dcfcd0274de2ba05a88893dc21b3/9215dcfcd0274de2ba05a88893dc21b3-1-1/5/26327280001.txt");
        textFieldConstancia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textFieldConstanciaKeyReleased(evt);
            }
        });

        labelStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        layerPanePrincipal.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout panelBusquedaLayout = new javax.swing.GroupLayout(panelBusqueda);
        panelBusqueda.setLayout(panelBusquedaLayout);
        panelBusquedaLayout.setHorizontalGroup(
            panelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(layerPanePrincipal)
                    .addGroup(panelBusquedaLayout.createSequentialGroup()
                        .addComponent(btnValidar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimpiar))
                    .addGroup(panelBusquedaLayout.createSequentialGroup()
                        .addGroup(panelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelBusquedaLayout.createSequentialGroup()
                                .addGroup(panelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(51, 51, 51))
                            .addGroup(panelBusquedaLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(labelStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(panelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textFieldURLArchivo, javax.swing.GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE)
                            .addGroup(panelBusquedaLayout.createSequentialGroup()
                                .addComponent(textFieldIdentificador, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(textFieldConstancia, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panelBusquedaLayout.setVerticalGroup(
            panelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBusquedaLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(textFieldIdentificador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldConstancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(10, 10, 10)
                .addGroup(panelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textFieldURLArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addGap(10, 10, 10)
                .addGroup(panelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnValidar)
                    .addComponent(btnLimpiar))
                .addGap(19, 19, 19)
                .addComponent(layerPanePrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelPrincipal.add(panelBusqueda, java.awt.BorderLayout.CENTER);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Validador de Constancias");
        panelPrincipal.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jMenu1.setText("Configuraciones");

        checkBoxMenuProxy.setSelected(true);
        checkBoxMenuProxy.setText("Proxy");
        checkBoxMenuProxy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxMenuProxyActionPerformed(evt);
            }
        });
        jMenu1.add(checkBoxMenuProxy);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnValidarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidarActionPerformed
        textPaneSalida.setText("");
        final String id = textFieldIdentificador.getText().trim();
        final String url = textFieldURLArchivo.getText().trim();
        final String constancia = textFieldConstancia.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Favor de ingresar el identificador", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            textFieldIdentificador.requestFocus();
            return;
        }
        if (constancia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Favor de ingresar la constancia a validar", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            textFieldConstancia.requestFocus();
            return;
        }
        if (url.isEmpty() && archivoBase64.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Favor de ingresar la url o arrastre un archivo", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            textFieldURLArchivo.requestFocus();
            return;
        } else {
            if (!url.isEmpty()) {
                String regex = "^(http:\\/\\/|https:\\/\\/)[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*(:[0-9]{1,5})?(\\/.*)?$";
                if (!url.matches(regex)) {
                    setIconEstatus(false);
                    JOptionPane.showMessageDialog(this, "Ingrese una url correcta por favor", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
                    textFieldURLArchivo.requestFocus();
                    textFieldURLArchivo.selectAll();
                    return;
                } else {
                    String b64 = FileUtils.readFileFromURI(url);
                    if (null == b64) {
                        setIconEstatus(false);
                        JOptionPane.showMessageDialog(this, "No se pudo obtener el recurso", "Proceso no terminado", JOptionPane.ERROR_MESSAGE);
                        textFieldURLArchivo.requestFocus();
                        textFieldURLArchivo.selectAll();
                        return;
                    } else {
                        establecerBase64(b64);
                        setIconEstatus(true);
                        obetenerTipoArchivo(url);
                    }
                }
            }
        }
        //Se realiza la validacion de la constancia
        showLoading(LOADING_MODE.INDETERMINATE);
        Thread miHilo = new Thread(new Runnable() {
            @Override
            public void run() {
                ValidadorNOMImp validador = getObject(ValidadorNOMImp.class);
                try {
                    escribirTextPane(MIDDLELINE, keyWordStyle);
                    escribirTextPane(STARTLINE, keyWordStyle);
                    String result = validador.validaNOM2002(id, id + "." + archivoTipo, archivoBase64, constancia);
                    if (null != result) {
                        escribirImagen((result.contains("\"Valida\"")));
                        escribirTextPane("\n", null);
                        escribirTextPane(result, keyWordStyleAzul);
                    } else {
                        escribirImagen(false);
                        escribirTextPane("\n", null);
                        escribirTextPane("Hubo un problema de conexión con el servicio de validación, favor de intentar más tarde.", keyWordStyleRojo);
                    }
                    escribirTextPane(ENDLINE, keyWordStyle);
                    escribirTextPane(MIDDLELINE, keyWordStyle);
                    escribirTextPane("\n", null);

                } catch (IOException | ParserConfigurationException | SOAPException | JAXBException ex) {
                    LOGGER.info("No se pudo realizar la validacion.", ex);
                    escribirTextPane("\n", null);
                    escribirTextPane("\n", null);
                    escribirTextPane("Hubo un problema de conexión con el servicio de validación, favor de intentar más tarde.", keyWordStyleRojo);
                    escribirTextPane("\n", null);
                    escribirTextPane("\n", null);
                    escribirTextPane(ENDLINE, keyWordStyle);
                }
                archivoBase64 = "";
                archivoTipo = "";
                labelStatus.setIcon(null);
            }
        });
        miHilo.start();
        new ThreatChecarProceso(miHilo).start();
    }//GEN-LAST:event_btnValidarActionPerformed

    private void textFieldConstanciaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldConstanciaKeyReleased
        if (textFieldConstancia.getText().contains("@")) {
            String dat[] = textFieldConstancia.getText().split("@");
            textFieldConstancia.setText(dat[0]);
            textFieldIdentificador.setText(dat[1]);
        } else if (textFieldConstancia.getText().trim().startsWith("http") && textFieldConstancia.getText().trim().endsWith("txt")) {
            String constancia = FileUtils.readFileFromURIContent(textFieldConstancia.getText());
            if (null != constancia) {
                textFieldConstancia.setText(constancia);
            }
        }
    }//GEN-LAST:event_textFieldConstanciaKeyReleased

    private void checkBoxMenuProxyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxMenuProxyActionPerformed
        checkBoxMenuProxy.getState();
    }//GEN-LAST:event_checkBoxMenuProxyActionPerformed

    private void limpiarCampos() {
        textFieldIdentificador.setText("");
        textFieldURLArchivo.setText("");
        textFieldConstancia.setText("");
        textPaneSalida.setText("");
        labelStatus.setIcon(null);
        archivoBase64 = "";
        archivoTipo = "";
    }

    public void setIconEstatus(boolean estatus) {
        labelStatus.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource(String.format("images/%s.jpg", (estatus) ? "ok" : "no"))).getImage()));
    }

    public void establecerBase64(String b64) {
        this.archivoBase64 = b64;
    }

    public void obetenerTipoArchivo(String archivo) {
        if (archivo.endsWith("pdf")) {
            archivoTipo = "pdf";
        } else {
            archivoTipo = "tif";
        }
    }

    public void mostrarMensajeVista(String texto, String title, int type) {
        JOptionPane.showMessageDialog(this, texto, title, type);
    }
    
    public static boolean useProxy(){
        LOGGER.info("proxy: " + checkBoxMenuProxy.getState());
        return checkBoxMenuProxy.getState();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    public static <R extends Object> R getObject(Class<? extends Object> clazz) {
        return (R) applicationContext.getBean(clazz);
    }

    public static void escribirTextPane(String linea, AttributeSet style) {
        try {
            contenidoDoc.insertString(contenidoDoc.getLength(), linea + "\n", style);
        } catch (BadLocationException ex) {
            System.out.println("No se pudo escribir: " + ex.getMessage());
        }
    }
    
    public void escribirImagen(boolean exito){
        Style def = StyleContext.getDefaultStyleContext().
                getStyle(StyleContext.DEFAULT_STYLE);
        Style regular = contenidoDoc.addStyle("regular", def);
        Style s = contenidoDoc.addStyle("icon", regular);
        StyleConstants.setAlignment(s, StyleConstants.ALIGN_CENTER);
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(String.format("images/%s.png", (exito) ? "aprobado" : "rechazado")), "hey");
        StyleConstants.setIcon(s, icon);
        try {
            contenidoDoc.insertString(contenidoDoc.getLength(), " ", contenidoDoc.getStyle("icon"));
        } catch (BadLocationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void showLoading(LOADING_MODE mode) {
        if (mode.equals(LOADING_MODE.INDETERMINATE)) {
            dialogLoading.setIndeterminateMode();
        }
        if (mode.equals(LOADING_MODE.SCALE)) {
            dialogLoading.setScaleMode();
        }
        dialogLoading.setVisible(true);
    }

    public static void hideLoading() {
        dialogLoading.dispose();
        LOGGER.info("Se manda a ocultar el loading");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnValidar;
    private static javax.swing.JCheckBoxMenuItem checkBoxMenuProxy;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JLayeredPane layerPanePrincipal;
    private javax.swing.JPanel panelBusqueda;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JTextField textFieldConstancia;
    private javax.swing.JTextField textFieldIdentificador;
    private javax.swing.JTextField textFieldURLArchivo;
    // End of variables declaration//GEN-END:variables
}
