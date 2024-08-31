package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

class Gider {
    private String tarih;
    private String aciklama;
    private double miktar;

    public Gider(String tarih, String aciklama, double miktar) {
        this.tarih = tarih;
        this.aciklama = aciklama;
        this.miktar = miktar;
    }

    @Override
    public String toString() {
        return "Tarih: " + tarih + ", Açıklama: " + aciklama + ", Miktar: " + miktar + " TL";
    }

    public double getMiktar() {
        return miktar;
    }
}

class AidatTakip {
    private List<Gider> giderler;

    public AidatTakip() {
        giderler = new ArrayList<>();
    }

    public void giderEkle(String tarih, String aciklama, double miktar) {
        giderler.add(new Gider(tarih, aciklama, miktar));
    }

    public List<Gider> getGiderler() {
        return giderler;
    }

    public double toplamGider() {
        return giderler.stream().mapToDouble(Gider::getMiktar).sum();
    }
}

public class ApartmanAidatTakipGUI extends JFrame {
    private AidatTakip aidatTakip;
    private JTextField tarihField, aciklamaField, miktarField;
    private JTextArea giderlerArea;
    private JLabel toplamLabel;

    public ApartmanAidatTakipGUI() {
        aidatTakip = new AidatTakip();
        setTitle("Apartman Aidat Takip Programı");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Gider ekleme paneli
        JPanel giderEklePanel = new JPanel(new GridLayout(4, 2));
        giderEklePanel.add(new JLabel("Tarih (GG.AA.YYYY):"));
        tarihField = new JTextField();
        giderEklePanel.add(tarihField);
        giderEklePanel.add(new JLabel("Açıklama:"));
        aciklamaField = new JTextField();
        giderEklePanel.add(aciklamaField);
        giderEklePanel.add(new JLabel("Miktar (TL):"));
        miktarField = new JTextField();
        giderEklePanel.add(miktarField);

        JButton ekleButton = new JButton("Gider Ekle");
        ekleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                giderEkle();
            }
        });
        giderEklePanel.add(ekleButton);

        add(giderEklePanel, BorderLayout.NORTH);

        // Giderleri görüntüleme alanı
        giderlerArea = new JTextArea();
        giderlerArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(giderlerArea);
        add(scrollPane, BorderLayout.CENTER);

        // Toplam gider paneli
        JPanel toplamPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        toplamLabel = new JLabel("Toplam Gider: 0.00 TL");
        toplamPanel.add(toplamLabel);
        add(toplamPanel, BorderLayout.SOUTH);
    }

    private void giderEkle() {
        try {
            String tarih = tarihField.getText();
            String aciklama = aciklamaField.getText();
            double miktar = Double.parseDouble(miktarField.getText());

            aidatTakip.giderEkle(tarih, aciklama, miktar);
            giderleriGuncelle();
            toplamGuncelle();

            // Alanları temizle
            tarihField.setText("");
            aciklamaField.setText("");
            miktarField.setText("");

            JOptionPane.showMessageDialog(this, "Gider başarıyla eklendi.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Geçersiz miktar girişi. Lütfen sayı giriniz.", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void giderleriGuncelle() {
        StringBuilder sb = new StringBuilder();
        for (Gider gider : aidatTakip.getGiderler()) {
            sb.append(gider.toString()).append("\n");
        }
        giderlerArea.setText(sb.toString());
    }

    private void toplamGuncelle() {
        toplamLabel.setText(String.format("Toplam Gider: %.2f TL", aidatTakip.toplamGider()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ApartmanAidatTakipGUI().setVisible(true);
            }
        });
    }
}