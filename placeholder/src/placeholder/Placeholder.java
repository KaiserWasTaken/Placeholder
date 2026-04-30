package placeholder;

import java_cup.runtime.Symbol;
import java_cup.runtime.ComplexSymbolFactory;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.*;
import java.awt.*;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import placeholder.analizadores.Lexer;
import placeholder.analizadores.Parser;
import placeholder.analizadores.sym;

/**
 * Placeholder.java — Analizador Zapoteco
 *
 * Pestañas:
 *   1. Tokens         → tabla léxica
 *   2. Resultado      → traza de ejecución (quién vale qué, en orden)
 *   3. Símbolos       → tabla de símbolos (nombre | tipo | valor | línea)
 *   4. Excepciones    → errores léxicos / sintácticos / semánticos
 */
public class Placeholder extends JFrame {

    // =========================================================
    //  PALETA
    // =========================================================
    private static final Color BG_MAIN      = new Color(245, 245, 245);
    private static final Color BG_PANEL     = new Color(255, 255, 255);
    private static final Color COLOR_ACCENT = new Color(217, 130, 43);
    private static final Color COLOR_BORDER = new Color(215, 215, 215);
    private static final Color TEXT_LIGHT   = new Color(40,  40,  40);
    private static final Color TEXT_MUTED   = new Color(110, 110, 110);
    private static final Color PILL_BG      = new Color(255, 243, 230);
    private static final Color PILL_TEXT    = new Color(180, 80,  10);

    // colores para la traza
    private static final Color COLOR_OK     = new Color(20,  130, 60);
    private static final Color COLOR_ERR    = new Color(190, 30,  30);
    private static final Color COLOR_INFO   = new Color(30,  90,  200);
    private static final Color COLOR_VAL    = new Color(130, 0,   160);

    private static final Font FUENTE_MONO  = new Font("Consolas", Font.PLAIN,  15);
    private static final Font FUENTE_UI    = new Font("Segoe UI",  Font.PLAIN,  13);
    private static final Font FUENTE_UI_B  = new Font("Segoe UI",  Font.BOLD,   13);
    private static final Font FUENTE_SMALL = new Font("Segoe UI",  Font.BOLD,   11);

    // =========================================================
    //  COMPONENTES
    // =========================================================
    private JTextPane editorCodigo;

    private DefaultTableModel modeloTokens;
    private JTable            tablaTokens;

    private JTextPane salidaResultado;      // traza

    private DefaultTableModel modeloSimbolos;
    private JTable            tablaSimbolos;

    private JTextPane salidaExcepciones;

    private JButton btnAnalizar;
    private JButton btnLimpiar;
    private JButton btnEjemplo;

    // =========================================================
    //  CONSTRUCTOR
    // =========================================================
    public Placeholder() {
        super("Analizador Zapoteco");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 700));
        setPreferredSize(new Dimension(1280, 780));
        getContentPane().setBackground(BG_MAIN);
        setLayout(new BorderLayout());

        UIManager.put("Panel.background",            BG_MAIN);
        UIManager.put("OptionPane.background",        BG_MAIN);
        UIManager.put("OptionPane.messageForeground", TEXT_LIGHT);

        add(crearBarraSuperior(), BorderLayout.NORTH);
        add(crearCuerpo(),        BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        agregarCodigoEjemplo();
    }

    // =========================================================
    //  BARRA SUPERIOR
    // =========================================================
    private JPanel crearBarraSuperior() {
        JPanel barra = new JPanel(new BorderLayout());
        barra.setBackground(BG_MAIN);
        barra.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 1, 0, COLOR_BORDER),
                new EmptyBorder(12, 20, 12, 20)));

        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panelTitulo.setOpaque(false);
        JLabel punto = new JLabel("●");
        punto.setForeground(COLOR_ACCENT);
        punto.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel titulo = new JLabel("Analizador Zapoteco");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(TEXT_LIGHT);
        panelTitulo.add(punto);
        panelTitulo.add(titulo);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setOpaque(false);

        btnLimpiar  = crearBotonPill("Limpiar",     false);
        btnEjemplo  = crearBotonPill("Ejemplo",     false);
        btnAnalizar = crearBotonPill("▶ Analizar",  true);

        btnAnalizar.addActionListener(e -> ejecutarAnalisis());
        btnLimpiar .addActionListener(e -> limpiarTodo());
        btnEjemplo .addActionListener(e -> agregarCodigoEjemplo());

        panelBotones.add(btnLimpiar);
        panelBotones.add(btnEjemplo);
        panelBotones.add(btnAnalizar);

        barra.add(panelTitulo,  BorderLayout.WEST);
        barra.add(panelBotones, BorderLayout.EAST);
        return barra;
    }

    private JButton crearBotonPill(String texto, boolean isPrimary) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                if      (getModel().isPressed())  g2.setColor(new Color(220, 220, 220));
                else if (getModel().isRollover()) g2.setColor(new Color(235, 235, 235));
                else                              g2.setColor(BG_MAIN);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.setColor(COLOR_BORDER);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FUENTE_UI_B);
        btn.setForeground(TEXT_LIGHT);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(isPrimary ? new Dimension(120, 32) : new Dimension(100, 32));
        return btn;
    }

    // =========================================================
    //  CUERPO
    // =========================================================
    private Component crearCuerpo() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                crearPanelEditor(),
                crearPanelDerecho());
        split.setResizeWeight(0.35);
        split.setDividerSize(4);
        split.setBorder(null);
        split.setBackground(BG_MAIN);
        split.setUI(new javax.swing.plaf.basic.BasicSplitPaneUI() {
            public javax.swing.plaf.basic.BasicSplitPaneDivider createDefaultDivider() {
                return new javax.swing.plaf.basic.BasicSplitPaneDivider(this) {
                    public void setBorder(Border b) {}
                    @Override public void paint(Graphics g) {
                        g.setColor(COLOR_BORDER);
                        g.fillRect(0, 0, getSize().width, getSize().height);
                        super.paint(g);
                    }
                };
            }
        });
        return split;
    }

    // ---------------------------------------------------------
    //  EDITOR
    // ---------------------------------------------------------
    private JPanel crearPanelEditor() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_PANEL);

        JLabel lbl = new JLabel("  EDITOR DE CÓDIGO");
        lbl.setFont(FUENTE_SMALL);
        lbl.setForeground(TEXT_MUTED);
        lbl.setPreferredSize(new Dimension(100, 35));
        lbl.setBorder(new MatteBorder(0, 0, 1, 0, COLOR_BORDER));

        editorCodigo = new JTextPane();
        editorCodigo.setFont(FUENTE_MONO);
        editorCodigo.setBackground(BG_PANEL);
        editorCodigo.setForeground(TEXT_LIGHT);
        editorCodigo.setCaretColor(TEXT_LIGHT);
        editorCodigo.setBorder(new EmptyBorder(15, 15, 15, 15));

        JScrollPane scroll = new JScrollPane(editorCodigo);
        scroll.setBorder(new MatteBorder(0, 0, 0, 1, COLOR_BORDER));
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        panel.add(lbl,    BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    // ---------------------------------------------------------
    //  PANEL DERECHO  (Tabs + Diccionario)
    // ---------------------------------------------------------
    private JSplitPane crearPanelDerecho() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                crearPanelTabs(),
                crearPanelDiccionario());
        split.setResizeWeight(0.70);
        split.setDividerSize(2);
        split.setBorder(null);
        split.setBackground(BG_MAIN);
        return split;
    }

    // ---------------------------------------------------------
    //  TABS
    // ---------------------------------------------------------
    private JPanel crearPanelTabs() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_MAIN);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(FUENTE_UI_B);
        tabs.setBackground(BG_MAIN);
        tabs.setForeground(TEXT_MUTED);
        tabs.setFocusable(false);
        tabs.setBorder(new MatteBorder(0, 0, 0, 1, COLOR_BORDER));

        // ── Pestaña 1: Tokens ────────────────────────────────
        String[] colsTokens = {"LEXEMA", "PATRÓN", "TOKEN", "LÍNEA"};
        modeloTokens = new DefaultTableModel(colsTokens, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaTokens = new JTable(modeloTokens);
        configurarEstiloTablaTokens(tablaTokens);

        JScrollPane scrollTokens = new JScrollPane(tablaTokens);
        scrollTokens.setBorder(null);
        scrollTokens.getViewport().setBackground(BG_MAIN);

        // ── Pestaña 2: Resultado (traza) ──────────────────────
        salidaResultado = crearAreaTexto();

        // ── Pestaña 3: Tabla de Símbolos ─────────────────────
        String[] colsSimbolos = {"IDENTIFICADOR", "TIPO", "VALOR", "LÍNEA"};
        modeloSimbolos = new DefaultTableModel(colsSimbolos, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaSimbolos = new JTable(modeloSimbolos);
        configurarEstiloTablaSimbolos(tablaSimbolos);

        JScrollPane scrollSimbolos = new JScrollPane(tablaSimbolos);
        scrollSimbolos.setBorder(null);
        scrollSimbolos.getViewport().setBackground(BG_MAIN);

        // ── Pestaña 4: Excepciones ────────────────────────────
        salidaExcepciones = crearAreaTexto();

        tabs.addTab("Tokens",      scrollTokens);
        tabs.addTab("Resultado",   new JScrollPane(salidaResultado));
        tabs.addTab("Símbolos",    scrollSimbolos);
        tabs.addTab("Excepciones", new JScrollPane(salidaExcepciones));

        panel.add(tabs, BorderLayout.CENTER);
        return panel;
    }

    // =========================================================
    //  ESTILOS DE TABLA — TOKENS
    // =========================================================
    private void configurarEstiloTablaTokens(JTable tabla) {
        tabla.setBackground(BG_MAIN);
        tabla.setForeground(TEXT_LIGHT);
        tabla.setFont(FUENTE_UI);
        tabla.setRowHeight(35);
        tabla.setShowGrid(true);
        tabla.setGridColor(COLOR_BORDER);
        tabla.setBorder(null);

        JTableHeader h = tabla.getTableHeader();
        h.setBackground(BG_MAIN);
        h.setForeground(TEXT_MUTED);
        h.setFont(FUENTE_SMALL);
        h.setPreferredSize(new Dimension(100, 35));
        h.setBorder(new MatteBorder(0, 0, 1, 0, COLOR_BORDER));

        // Columna TOKEN → píldora
        tabla.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object value, boolean sel, boolean foc, int row, int col) {
                JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
                p.setBackground(sel ? t.getSelectionBackground() : BG_MAIN);
                p.setBorder(new MatteBorder(0, 0, 1, 0, COLOR_BORDER));
                JLabel pill = new JLabel(" " + value + " ") {
                    @Override protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                            RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(PILL_BG);
                        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                        g2.dispose();
                        super.paintComponent(g);
                    }
                };
                pill.setFont(FUENTE_SMALL);
                pill.setForeground(PILL_TEXT);
                p.add(pill);
                return p;
            }
        });

        DefaultTableCellRenderer base = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                ((JComponent) c).setBorder(new CompoundBorder(
                        new MatteBorder(0, 0, 1, 0, COLOR_BORDER),
                        new EmptyBorder(0, 10, 0, 0)));
                return c;
            }
        };
        tabla.getColumnModel().getColumn(0).setCellRenderer(base);
        tabla.getColumnModel().getColumn(1).setCellRenderer(base);
        tabla.getColumnModel().getColumn(3).setCellRenderer(base);
    }

    // =========================================================
    //  ESTILOS DE TABLA — SÍMBOLOS
    // =========================================================
    private void configurarEstiloTablaSimbolos(JTable tabla) {
        tabla.setBackground(BG_MAIN);
        tabla.setForeground(TEXT_LIGHT);
        tabla.setFont(FUENTE_UI);
        tabla.setRowHeight(35);
        tabla.setShowGrid(true);
        tabla.setGridColor(COLOR_BORDER);
        tabla.setBorder(null);

        JTableHeader h = tabla.getTableHeader();
        h.setBackground(BG_MAIN);
        h.setForeground(TEXT_MUTED);
        h.setFont(FUENTE_SMALL);
        h.setPreferredSize(new Dimension(100, 35));
        h.setBorder(new MatteBorder(0, 0, 1, 0, COLOR_BORDER));

        // Columna TIPO → píldora con color diferenciado
        tabla.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object value, boolean sel, boolean foc, int row, int col) {
                JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
                p.setBackground(sel ? t.getSelectionBackground() : BG_MAIN);
                p.setBorder(new MatteBorder(0, 0, 1, 0, COLOR_BORDER));

                String tipo = value != null ? value.toString() : "";
                Color bg, fg;
                switch (tipo) {
                    case "ENTERO":  bg = new Color(230, 245, 255); fg = new Color(0,   80, 160); break;
                    case "DECIMAL": bg = new Color(245, 235, 255); fg = new Color(100, 0,  160); break;
                    case "CADENA":  bg = new Color(255, 245, 230); fg = new Color(160, 70, 0);   break;
                    default:        bg = PILL_BG; fg = PILL_TEXT; break;
                }

                JLabel pill = new JLabel(" " + tipo + " ") {
                    @Override protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                            RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(bg);
                        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                        g2.dispose();
                        super.paintComponent(g);
                    }
                };
                pill.setFont(FUENTE_SMALL);
                pill.setForeground(fg);
                p.add(pill);
                return p;
            }
        });

        DefaultTableCellRenderer base = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                ((JComponent) c).setBorder(new CompoundBorder(
                        new MatteBorder(0, 0, 1, 0, COLOR_BORDER),
                        new EmptyBorder(0, 10, 0, 0)));
                return c;
            }
        };
        tabla.getColumnModel().getColumn(0).setCellRenderer(base);
        tabla.getColumnModel().getColumn(2).setCellRenderer(base);
        tabla.getColumnModel().getColumn(3).setCellRenderer(base);
    }

    // =========================================================
    //  ANÁLISIS CENTRAL
    // =========================================================
    private void ejecutarAnalisis() {
        String codigo = editorCodigo.getText().trim();
        limpiarResultados();

        if (codigo.isEmpty()) {
            appendTexto(salidaExcepciones, "Sin código para analizar.\n", COLOR_ERR, false);
            return;
        }

        // ── FASE 1: LÉXICO ─────────────────────────────────────
        List<Symbol> tokens = new ArrayList<>();
        try {
            Lexer lexer = new Lexer(new StringReader(codigo));
            Symbol tok;
            while ((tok = lexer.next_token()).sym != sym.EOF) {
                tokens.add(tok);
            }
            mostrarTokensEnTabla(tokens);
        } catch (Exception ex) {
            appendTexto(salidaExcepciones,
                    "✖  Error Léxico\n\n" + ex.getMessage() + "\n", COLOR_ERR, false);
            return;
        }
        // ── FASE 2: SINTÁCTICO + SEMÁNTICO ─────────────────────
        try {
            java_cup.runtime.DefaultSymbolFactory sf = new java_cup.runtime.DefaultSymbolFactory();
            Lexer lexer2 = new Lexer(new StringReader(codigo));
            Parser parser = new Parser(lexer2, sf);
            parser.parse(); // <-- Si esto pasa sin tirar error, el código es válido

            salidaResultado.setText("✔ Análisis Sintáctico y Semántico completado sin errores.\n\n");
            salidaExcepciones.setText("0 excepciones detectadas.");

            // ¡AQUÍ ES DONDE DEBES LLAMARLO!
            construirTrazaYSimbolos(tokens);
        }catch (Exception ex) {
            String msg = ex.getMessage() != null ? ex.getMessage()
                                                 : ex.getClass().getSimpleName();
            appendTexto(salidaExcepciones,
                    "✖  Error Sintáctico / Semántico\n\n" + msg + "\n", COLOR_ERR, false);
        }
    }

    // =========================================================
    //  TRAZA DE EJECUCIÓN + TABLA DE SÍMBOLOS
    //
    //  Recorre la lista de tokens (ya validada por el parser) e
    //  interpreta cada sentencia:
    //
    //    TIPO  ID  ali  EXPRESION  ;
    //    ID    ali  EXPRESION  ;
    //
    //  EXPRESION puede ser:  VAL_ENTERO | VAL_DECIMAL | VAL_CADENA
    //                      | ID  (variable ya declarada)
    //                      | EXPR OP EXPR   (aritmética / concat)
    //
    //  Para evaluar la expresión usamos la tabla de símbolos local
    //  (Map<nombre, Object>) que vamos construyendo en orden.
    // =========================================================
    private void construirTrazaYSimbolos(List<Symbol> tokens) {

        // tabla local: nombre → valor (Integer, Double, String)
        Map<String, Object> tablaValores = new LinkedHashMap<>();
        // tabla local: nombre → tipo
        Map<String, String>  tablaTipos  = new LinkedHashMap<>();
        // tabla local: nombre → línea de declaración (1-based)
        Map<String, Integer> tablaLineas = new LinkedHashMap<>();

        StyledDocument doc = salidaResultado.getStyledDocument();

        // Cabecera de la traza
        appendTexto(salidaResultado, "── Traza de Ejecución ──────────────────────────\n\n",
                TEXT_MUTED, true);

        int i = 0;
        while (i < tokens.size()) {
            Symbol t = tokens.get(i);

            // Determinar si la sentencia empieza con un tipo o con un identificador
            boolean tieneDeclaracion = (t.sym == sym.PR_ENTERO
                                     || t.sym == sym.PR_DECIMAL
                                     || t.sym == sym.PR_CADENA);

            String tipoDeclarado = null;
            String nombreVar     = null;
            int    lineaDecl     = t.left + 1; // JFlex es 0-based

            if (tieneDeclaracion) {
                tipoDeclarado = tipoDesdeSymbol(t.sym);
                i++;
                if (i >= tokens.size()) break;
                nombreVar = tokenValor(tokens.get(i));
                i++;
            } else if (t.sym == sym.IDENTIFICADOR) {
                nombreVar = tokenValor(t);
                tipoDeclarado = tablaTipos.getOrDefault(nombreVar, "DESCONOCIDO");
                i++;
            } else {
                i++; // token inesperado, avanzar
                continue;
            }

            // Esperamos OP_ASIG  (ali)
            if (i >= tokens.size() || tokens.get(i).sym != sym.OP_ASIG) {
                i++;
                continue;
            }
            i++; // consumir ali

            // Recolectar tokens de la EXPRESION hasta el PUNTO_COMA
            List<Symbol> exprTokens = new ArrayList<>();
            while (i < tokens.size() && tokens.get(i).sym != sym.PUNTO_COMA) {
                exprTokens.add(tokens.get(i));
                i++;
            }
            if (i < tokens.size()) i++; // consumir ;

            // Evaluar la expresión
            Object valor = evaluarExpresion(exprTokens, tablaValores);

            // Actualizar tablas locales
            tablaValores.put(nombreVar, valor);
            tablaTipos  .put(nombreVar, tipoDeclarado);
            tablaLineas .put(nombreVar, lineaDecl);

            // ── Escribir en la traza ──────────────────────────
            // "  L3  resultado  =  16"
            String linPref = String.format("  L%-3d  ", lineaDecl);
            appendTexto(salidaResultado, linPref, TEXT_MUTED, false);

            appendTexto(salidaResultado, nombreVar, COLOR_INFO, true);
            appendTexto(salidaResultado, "  =  ",   TEXT_LIGHT, false);
            appendTexto(salidaResultado, formatearValor(valor, tipoDeclarado),
                    tipoDeclarado.equals("CADENA") ? new Color(160, 70, 0) : COLOR_VAL,
                    false);

            // Mostrar la expresión original como contexto
            String exprStr = exprTokensAString(exprTokens);
            appendTexto(salidaResultado,
                    "        ← " + exprStr + "\n", TEXT_MUTED, false);
        }

        appendTexto(salidaResultado,
                "\n── Fin de la ejecución ─────────────────────────\n",
                TEXT_MUTED, true);

        // ── Poblar la tabla de símbolos ───────────────────────
        modeloSimbolos.setRowCount(0);
        for (Map.Entry<String, Object> entry : tablaValores.entrySet()) {
            String nom  = entry.getKey();
            String tipo = tablaTipos .getOrDefault(nom, "?");
            String val  = formatearValor(entry.getValue(), tipo);
            int    lin  = tablaLineas .getOrDefault(nom, 0);
            modeloSimbolos.addRow(new Object[]{nom, tipo, val, "L" + lin});
        }
    }

    // =========================================================
    //  EVALUADOR DE EXPRESIONES
    //
    //  Soporta:  literal | id | expr OP expr
    //  Aplica precedencia mediante recursión con índice.
    //  Para Placeholder:  +,-,*,/ solo con enteros/decimales;
    //                     tzo (concat) solo con cadenas.
    // =========================================================
    private Object evaluarExpresion(List<Symbol> toks, Map<String, Object> tabla) {
        if (toks.isEmpty()) return null;

        // Buscar el operador de menor precedencia (más a la derecha para
        // respetar asociatividad izquierda): primero + y -, luego * y /,
        // luego tzo (OP_CONCAT).
        // Recorremos de derecha a izquierda para +/-
        int idx = buscarOperador(toks, sym.OP_SUMA, sym.OP_RESTA);
        if (idx > 0) {
            Object izq = evaluarExpresion(toks.subList(0, idx), tabla);
            Object der = evaluarExpresion(toks.subList(idx + 1, toks.size()), tabla);
            return aplicarOp(toks.get(idx).sym, izq, der);
        }

        idx = buscarOperador(toks, sym.OP_MULT, sym.OP_DIV);
        if (idx > 0) {
            Object izq = evaluarExpresion(toks.subList(0, idx), tabla);
            Object der = evaluarExpresion(toks.subList(idx + 1, toks.size()), tabla);
            return aplicarOp(toks.get(idx).sym, izq, der);
        }

        idx = buscarOperador(toks, sym.OP_CONCAT, -1);
        if (idx > 0) {
            Object izq = evaluarExpresion(toks.subList(0, idx), tabla);
            Object der = evaluarExpresion(toks.subList(idx + 1, toks.size()), tabla);
            return String.valueOf(izq) + String.valueOf(der);
        }

        // Expresión atómica (un solo token)
        if (toks.size() == 1) {
            Symbol s = toks.get(0);
            if (s.sym == sym.VAL_ENTERO)  return Integer.parseInt(tokenValor(s));
            if (s.sym == sym.VAL_DECIMAL) return Double.parseDouble(tokenValor(s));
            if (s.sym == sym.VAL_CADENA)  return sinComillas(tokenValor(s));
            if (s.sym == sym.IDENTIFICADOR) {
                return tabla.getOrDefault(tokenValor(s), "?");
            }
        }
        return null;
    }

    /** Busca el último operador con sym == op1 o sym == op2, de derecha a izquierda. */
    private int buscarOperador(List<Symbol> toks, int op1, int op2) {
        for (int i = toks.size() - 1; i >= 0; i--) {
            int s = toks.get(i).sym;
            if (s == op1 || (op2 != -1 && s == op2)) return i;
        }
        return -1;
    }

    private Object aplicarOp(int op, Object a, Object b) {
        if (a == null || b == null) return null;
        try {
            if (a instanceof String || b instanceof String) {
                return String.valueOf(a) + String.valueOf(b);
            }
            double da = toDouble(a);
            double db = toDouble(b);
            double resultado;
            switch (op) {
                case sym.OP_SUMA:  resultado = da + db; break;
                case sym.OP_RESTA: resultado = da - db; break;
                case sym.OP_MULT:  resultado = da * db; break;
                case sym.OP_DIV:   resultado = db != 0 ? da / db : Double.NaN; break;
                default: return null;
            }
            // Si ambos operandos eran enteros y el resultado es entero, devolver int
            if (a instanceof Integer && b instanceof Integer
                    && op != sym.OP_DIV && resultado == (int) resultado) {
                return (int) resultado;
            }
            return resultado;
        } catch (Exception e) {
            return null;
        }
    }

    private double toDouble(Object o) {
        if (o instanceof Integer) return ((Integer) o).doubleValue();
        if (o instanceof Double)  return (Double) o;
        return 0;
    }

    // =========================================================
    //  LLENADO DE TABLA DE TOKENS
    // =========================================================
    private void mostrarTokensEnTabla(List<Symbol> tokens) {
        modeloTokens.setRowCount(0);
        for (Symbol s : tokens) {
            String lexema  = s.value != null ? s.value.toString() : nombreToken(s.sym);
            String patron  = determinarPatron(s.sym, lexema);
            String categ   = agruparToken(s.sym);
            String linea   = "L" + (s.left + 1);
            modeloTokens.addRow(new Object[]{lexema, patron, categ, linea});
        }
    }

    // =========================================================
    //  MAPEOS AUXILIARES
    // =========================================================
    private String nombreToken(int id) {
        return (id >= 0 && id < sym.terminalNames.length) ? sym.terminalNames[id] : "DESCONOCIDO";
    }

    private String tokenValor(Symbol s) {
        return s.value != null ? s.value.toString() : "";
    }

    private String tipoDesdeSymbol(int symId) {
        switch (symId) {
            case sym.PR_ENTERO:  return "ENTERO";
            case sym.PR_DECIMAL: return "DECIMAL";
            case sym.PR_CADENA:  return "CADENA";
            default: return "DESCONOCIDO";
        }
    }

    private String sinComillas(String s) {
        if (s == null) return "";
        if (s.startsWith("\"") && s.endsWith("\"") && s.length() >= 2)
            return s.substring(1, s.length() - 1);
        return s;
    }

    private String formatearValor(Object val, String tipo) {
        if (val == null) return "null";
        if ("CADENA".equals(tipo)) return "\"" + val + "\"";
        if (val instanceof Double) {
            double d = (Double) val;
            if (d == (long) d) return String.valueOf((long) d) + ".0";
        }
        return String.valueOf(val);
    }

    private String exprTokensAString(List<Symbol> toks) {
        StringBuilder sb = new StringBuilder();
        for (Symbol s : toks) {
            sb.append(tokenValor(s)).append(" ");
        }
        return sb.toString().trim();
    }

    private String agruparToken(int id) {
        switch (id) {
            case sym.PR_ENTERO:
            case sym.PR_DECIMAL:
            case sym.PR_CADENA:     return "RESERVADA";
            case sym.VAL_ENTERO:
            case sym.VAL_DECIMAL:
            case sym.VAL_CADENA:    return "NUMERO/VAL";
            case sym.OP_SUMA:
            case sym.OP_RESTA:
            case sym.OP_MULT:
            case sym.OP_DIV:
            case sym.OP_CONCAT:
            case sym.OP_ASIG:       return "OPERADOR";
            case sym.PUNTO_COMA:    return "SEPARADOR";
            case sym.IDENTIFICADOR: return "IDENTIFICADOR";
            default:                return "DESCONOCIDO";
        }
    }

    private String determinarPatron(int id, String lexema) {
        if (agruparToken(id).equals("RESERVADA"))    return lexema;
        if (id == sym.IDENTIFICADOR)                 return "[A-Za-z][A-Za-z0-9]*";
        if (id == sym.VAL_ENTERO)                    return "[0-9]+";
        if (id == sym.VAL_DECIMAL)                   return "[0-9]+\\.[0-9]+";
        if (id == sym.VAL_CADENA)                    return "\\\".*\\\"";
        if (agruparToken(id).equals("OPERADOR"))     return "signo " + lexema;
        if (id == sym.PUNTO_COMA)                    return "signo ;";
        return lexema;
    }

    // =========================================================
    //  UTILIDADES DE TEXTO ESTILIZADO
    // =========================================================
    private JTextPane crearAreaTexto() {
        JTextPane area = new JTextPane();
        area.setFont(FUENTE_MONO);
        area.setEditable(false);
        area.setBackground(BG_MAIN);
        area.setForeground(TEXT_LIGHT);
        area.setBorder(new EmptyBorder(15, 15, 15, 15));
        return area;
    }

    private void appendTexto(JTextPane area, String texto, Color color, boolean bold) {
        StyledDocument doc = area.getStyledDocument();
        Style style = new javax.swing.text.StyleContext().addStyle(null, null);
        StyleConstants.setForeground(style, color);
        StyleConstants.setBold(style, bold);
        StyleConstants.setFontFamily(style, "Consolas");
        StyleConstants.setFontSize(style, 13);
        try { doc.insertString(doc.getLength(), texto, style); }
        catch (BadLocationException ignored) {}
    }

    // =========================================================
    //  LIMPIEZA
    // =========================================================
    private void limpiarResultados() {
        modeloTokens.setRowCount(0);
        salidaResultado.setText("");
        modeloSimbolos.setRowCount(0);
        salidaExcepciones.setText("");
    }

    private void limpiarTodo() {
        editorCodigo.setText("");
        limpiarResultados();
    }

    // =========================================================
    //  DICCIONARIO ZAPOTECO (sin cambios estéticos)
    // =========================================================
    private JPanel crearPanelDiccionario() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_PANEL);

        JLabel lblHeader = new JLabel("  DICCIONARIO");
        lblHeader.setFont(FUENTE_SMALL);
        lblHeader.setForeground(COLOR_ACCENT);
        lblHeader.setPreferredSize(new Dimension(100, 35));
        lblHeader.setBorder(new MatteBorder(0, 0, 1, 0, COLOR_BORDER));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BG_PANEL);
        content.setBorder(new EmptyBorder(10, 10, 10, 10));

        content.add(crearSeccionDic("TIPOS"));
        content.add(crearFilaDic("int",    "pidxi"));
        content.add(crearFilaDic("double", "pixa"));
        content.add(crearFilaDic("string", "didxa"));
        content.add(Box.createRigidArea(new Dimension(0, 10)));

        content.add(crearSeccionDic("OPERADORES"));
        content.add(crearFilaDic("+", "ruchi"));
        content.add(crearFilaDic("-", "ruya"));
        content.add(crearFilaDic("*", "stipa"));
        content.add(crearFilaDic("/", "chup"));
        content.add(Box.createRigidArea(new Dimension(0, 10)));

        content.add(crearSeccionDic("ASIGNACIÓN"));
        content.add(crearFilaDic("=",      "ali"));
        content.add(crearFilaDic("concat", "tzo"));

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        panel.add(lblHeader, BorderLayout.NORTH);
        panel.add(scroll,    BorderLayout.CENTER);
        return panel;
    }

    private JLabel crearSeccionDic(String titulo) {
        JLabel lbl = new JLabel(titulo);
        lbl.setFont(FUENTE_SMALL);
        lbl.setForeground(TEXT_MUTED);
        lbl.setBorder(new EmptyBorder(10, 0, 5, 0));
        return lbl;
    }

    private JPanel crearFilaDic(String nativo, String zapoteco) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_PANEL);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.setBorder(new MatteBorder(0, 0, 1, 0, COLOR_BORDER));

        JLabel lblNativo = new JLabel(nativo);
        lblNativo.setFont(FUENTE_UI_B);
        lblNativo.setForeground(TEXT_LIGHT);

        JPanel pillContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 4));
        pillContainer.setOpaque(false);
        JLabel pill = new JLabel(" " + zapoteco + " ") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(PILL_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        pill.setFont(FUENTE_SMALL);
        pill.setForeground(PILL_TEXT);
        pillContainer.add(pill);

        panel.add(lblNativo,     BorderLayout.WEST);
        panel.add(pillContainer, BorderLayout.EAST);
        return panel;
    }

    // =========================================================
    //  EJEMPLO
    // =========================================================
    private void agregarCodigoEjemplo() {
        editorCodigo.setText(
            "pidxi numero1 ali 8;\n" +
            "pidxi numero2 ali 8;\n" +
            "pidxi resultado ali numero1 stipa numero2;\n" 
        );
    }

    // =========================================================
    //  MAIN
    // =========================================================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}
            new Placeholder().setVisible(true);
        });
    }
}