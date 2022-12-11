package com.simba.tools;

/*
@Date 2022/12/9 18:47
@PackageName com.simba.tools
@Author liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/


import javax.swing.*;

public class JFrameExample {
    public static void main(String[] args) {
        // 主窗体
        JFrame frmMain = new JFrame("图形界面范例1");
        // 主窗体设置大小
        frmMain.setSize(800, 600);
        // 主窗体设置位置
        frmMain.setLocation(200, 200);
        // 主窗体中的组件设置为绝对定位
        frmMain.setLayout(null);
        // 按钮组件
        JButton btnOK = new JButton("按钮");

        //图片路径从包开始写而绝对路径是无法显示的

        String path = "com/simba/2.png";
        final JLabel lblImage = new JLabel();
        ImageIcon pic = new ImageIcon(ClassLoader.getSystemResource(path));

        lblImage.setIcon(pic);
        lblImage.setBounds(50, 50, pic.getIconWidth(), pic.getIconHeight());
        // 同时设置组件的大小和位置
        btnOK.setBounds(50, 50, 100, 30);
        btnOK.addActionListener(actionEvent -> {
            lblImage.setVisible(false);

        });
        // 把按钮加入到主窗体中
        frmMain.add(btnOK);
        // 关闭窗体的时候，退出程序
        frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 让窗体变得可见
        frmMain.setVisible(true);
    }
}
