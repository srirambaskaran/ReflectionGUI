package org.reflection.gui;

/** Reflection.java
 *Coded by sriramb,sgsshankar,hari raghav
 *released under creative commons license
 *http://creativecommons.org/ for more details
 */
import java.awt.BorderLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

class Reflection extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	JTextField text;
	List area, modifierArea;
	int methflag, consflag, fieldflag;
	JButton methodInfo, constructorInfo, fieldInfo;
	JButton paramInfo, modifierInfo, returnTypeInfo;
	JPanel p1;
	JPanel p2;
	JScrollPane s, t;

	public Reflection() {
		methflag = 0;
		consflag = 0;
		fieldflag = 0;
		text = new JTextField(30);
		area = new List();
		modifierArea = new List();
		s = new JScrollPane(area);
		t = new JScrollPane(modifierArea);
		methodInfo = new JButton("Methods");
		constructorInfo = new JButton("Constructors");
		fieldInfo = new JButton("Fields");
		paramInfo = new JButton("ParameterInfo");
		modifierInfo = new JButton("ModifierInfo");
		returnTypeInfo = new JButton("ReturnTypeInfo");
		methodInfo.addActionListener(this);
		constructorInfo.addActionListener(this);
		fieldInfo.addActionListener(this);
		paramInfo.addActionListener(this);
		modifierInfo.addActionListener(this);
		returnTypeInfo.addActionListener(this);
		p1 = new JPanel();
		p2 = new JPanel();
		p1.add(text);
		p1.add(methodInfo);
		p1.add(constructorInfo);
		p1.add(fieldInfo);
		p2.add(paramInfo);
		p2.add(modifierInfo);
		p2.add(returnTypeInfo);
		p2.setSize(10, 70);
		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		add(s, BorderLayout.WEST);
		add(t, BorderLayout.EAST);
		setTitle("Reflection");
		setSize(650, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@SuppressWarnings("rawtypes")
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == methodInfo) {
			try {
				methflag = 1;
				consflag = 0;
				fieldflag = 0;
				modifierInfo.setEnabled(false);
				paramInfo.setEnabled(true);
				returnTypeInfo.setEnabled(true);
				area.removeAll();
				modifierArea.removeAll();
				Class c = Class.forName(text.getText());
				Method methlist[] = c.getDeclaredMethods();
				if (methlist == null)
					area.add("No Declared Methods");
				for (Method m : methlist)
					area.add(m.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (ae.getSource() == paramInfo && methflag == 1) {
			modifierArea.removeAll();
			try {
				Class c = Class.forName(text.getText());
				int index = area.getSelectedIndex();
				System.out.println(index);
				Method methlist[] = c.getDeclaredMethods();
				Class param[] = methlist[index].getParameterTypes();
				if (param == null)
					modifierArea.add("No Parameters");
				for (int k = 0; k < param.length; k++)
					modifierArea.add(param[k].getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (ae.getSource() == returnTypeInfo && methflag == 1) {
			try {

				modifierArea.removeAll();
				int index = area.getSelectedIndex();
				Class c = Class.forName(text.getText());
				Method methlist[] = c.getDeclaredMethods();
				Class ret = methlist[index].getReturnType();
				modifierArea.add(ret.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (ae.getSource() == constructorInfo) {
			try {
				methflag = 0;
				consflag = 1;
				fieldflag = 0;
				modifierInfo.setEnabled(false);
				returnTypeInfo.setEnabled(false);
				paramInfo.setEnabled(true);
				area.removeAll();
				modifierArea.removeAll();
				Class c = Class.forName(text.getText());
				Constructor ctrlist[] = c.getDeclaredConstructors();
				if (ctrlist == null)
					area.add("No Constructors Defined");
				for (int i = 0; i < ctrlist.length; i++) {
					area.add(ctrlist[i].getName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (ae.getSource() == paramInfo && consflag == 1) {
			try {
				modifierArea.removeAll();
				int index = area.getSelectedIndex();
				Class c = Class.forName(text.getText());
				Constructor ctrlist[] = c.getDeclaredConstructors();
				Class param[] = ctrlist[index].getParameterTypes();
				if (param == null)
					modifierArea.add("No Parameters");
				for (int k = 0; k < param.length; k++)
					modifierArea.add(param[k].getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (ae.getSource() == fieldInfo) {
			try {
				methflag = 0;
				consflag = 0;
				fieldflag = 1;
				area.removeAll();
				modifierArea.removeAll();
				returnTypeInfo.setEnabled(false);
				modifierInfo.setEnabled(true);
				paramInfo.setEnabled(true);
				Class c = Class.forName(text.getText());
				Field fieldlist[] = c.getDeclaredFields();
				if (fieldlist == null)
					area.add("No Fields");
				for (int i = 0; i < fieldlist.length; i++) {
					area.add(fieldlist[i].getName());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (ae.getSource() == paramInfo && fieldflag == 1) {
			try {
				int index = modifierArea.getSelectedIndex();
				modifierArea.removeAll();
				Class c = Class.forName(text.getText());
				Field fieldlist[] = c.getDeclaredFields();
				modifierArea.add(fieldlist[index + 1].getType().toString());

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (ae.getSource() == modifierInfo && fieldflag == 1) {
			try {
				modifierArea.removeAll();
				int index = modifierArea.getSelectedIndex();
				Class c = Class.forName(text.getText());
				Field fieldlist[] = c.getDeclaredFields();
				int mod = fieldlist[index + 1].getModifiers();
				modifierArea.add(Modifier.toString(mod));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]) {
		new Reflection();
	}
}
