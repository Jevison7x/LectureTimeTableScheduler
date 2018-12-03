/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyneex.att.courses;

import com.xyneex.att.MainFrame;
import com.xyneex.att.beans.Course;
import com.xyneex.att.util.WindowsSetter;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Jevison7x
 */
public class CoursesListDialog extends JDialog
{
    private CoursesTableModel ctm;

    private TableRowSorter<CoursesTableModel> rowSorter;

    /**
     * Creates new form CoursesListDialog
     */
    public CoursesListDialog(MainFrame parent, List<Course> courses)
    {
        super(parent, true);
        this.ctm = new CoursesTableModel(courses);
        this.initRowSorter();
        this.initComponents();
        //this.setTableColumnWidths();
        this.setNumericColumnsAllignment();
        this.setTableColumnWidths();
        this.centralizeWindow();
    }

    protected void refreshTable()
    {
        try
        {
            List<Course> courses = CoursesDAO.getAllCourses(((MainFrame)this.getParent()).getDepartment());
            this.ctm = new CoursesTableModel(courses);
            this.initRowSorter();
            this.coursesTable.setModel(this.ctm);
            this.coursesTable.setRowSorter(this.rowSorter);
            this.setTableColumnWidths();
            this.setNumericColumnsAllignment();
        }
        catch(ClassNotFoundException | IOException | SQLException xcp)
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, xcp.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
            xcp.printStackTrace(System.err);
        }
    }

    private void setNumericColumnsAllignment()
    {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

        this.coursesTable.getColumnModel().getColumn(this.ctm.findColumn(CoursesTableModel.SN)).setCellRenderer(rightRenderer);
        this.coursesTable.getColumnModel().getColumn(this.ctm.findColumn(CoursesTableModel.COURSE_CODE)).setCellRenderer(centerRenderer);
        this.coursesTable.getColumnModel().getColumn(this.ctm.findColumn(CoursesTableModel.YEAR_OF_STUDY)).setCellRenderer(centerRenderer);
        this.coursesTable.getColumnModel().getColumn(this.ctm.findColumn(CoursesTableModel.SEMESTER)).setCellRenderer(centerRenderer);
        this.coursesTable.getColumnModel().getColumn(this.ctm.findColumn(CoursesTableModel.CREDIT_HOUR)).setCellRenderer(centerRenderer);
    }

    private void setTableColumnWidths()
    {
        this.coursesTable.getColumnModel().getColumn(this.ctm.findColumn(CoursesTableModel.SN)).setPreferredWidth(2);
        this.coursesTable.getColumnModel().getColumn(this.ctm.findColumn(CoursesTableModel.COURSE_CODE)).setPreferredWidth(40);
        this.coursesTable.getColumnModel().getColumn(this.ctm.findColumn(CoursesTableModel.COURSE_TITLE)).setPreferredWidth(250);
        this.coursesTable.getColumnModel().getColumn(this.ctm.findColumn(CoursesTableModel.YEAR_OF_STUDY)).setPreferredWidth(40);
        this.coursesTable.getColumnModel().getColumn(this.ctm.findColumn(CoursesTableModel.SEMESTER)).setPreferredWidth(20);
        this.coursesTable.getColumnModel().getColumn(this.ctm.findColumn(CoursesTableModel.CREDIT_HOUR)).setPreferredWidth(40);
        this.coursesTable.getColumnModel().getColumn(this.ctm.findColumn(CoursesTableModel.EXT_INT)).setPreferredWidth(20);
        this.coursesTable.getColumnModel().getColumn(this.ctm.findColumn(CoursesTableModel.VENUES)).setPreferredWidth(250);
    }

    private void initRowSorter()
    {
        this.rowSorter = new TableRowSorter<>(this.ctm);
        int serialNoColIndex = this.ctm.findColumn(CoursesTableModel.SN);
        this.rowSorter.setComparator(serialNoColIndex, new IntegerComparator());
    }

    protected void tableChanged()
    {
        SwingUtilities.invokeLater(() ->
        {
            JScrollBar sb = CoursesListDialog.this.scrollPane.getVerticalScrollBar();
            sb.setValue(sb.getMaximum());
        });
    }

    private void centralizeWindow()
    {
        WindowsSetter.centralizeWindow(this);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        titleLbl = new javax.swing.JLabel();
        addBtn = new javax.swing.JButton();
        removeBtn = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        coursesTable = new javax.swing.JTable()
        {
            protected final String[] columnToolTips = CoursesListDialog.this.ctm.getColumnNames();
            //Implement table header tool tips.
            protected JTableHeader createDefaultTableHeader()
            {
                return new JTableHeader(columnModel)
                {
                    public String getToolTipText(MouseEvent e)
                    {
                        //String tip = null;
                        Point p = e.getPoint();
                        int index = columnModel.getColumnIndexAtX(p.x);
                        int realIndex = columnModel.getColumn(index).getModelIndex();
                        return columnToolTips[realIndex];
                    }
                };
            }
        }
        ;
        editCourseBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        titleLbl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        titleLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/xyneex/att/icons/List_of_Works_32.png"))); // NOI18N
        titleLbl.setText("Courses List");

        addBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/xyneex/att/icons/Add_button_inside_black_circle_32.png"))); // NOI18N
        addBtn.setText("Add Course");
        addBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                addBtnActionPerformed(evt);
            }
        });

        removeBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        removeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/xyneex/att/icons/Remove_Circular_Button_32.png"))); // NOI18N
        removeBtn.setText("Remove Course");
        removeBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                removeBtnActionPerformed(evt);
            }
        });

        coursesTable.setModel(this.ctm);
        coursesTable.setIntercellSpacing(new java.awt.Dimension(4, 1));
        coursesTable.setRowSorter(this.rowSorter);
        coursesTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseMoved(java.awt.event.MouseEvent evt)
            {
                coursesTableMouseMoved(evt);
            }
        });
        scrollPane.setViewportView(coursesTable);

        editCourseBtn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        editCourseBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/xyneex/att/icons/Pen_on_square_of_paper_interface_symbol_32.png"))); // NOI18N
        editCourseBtn.setText("Edit Course");
        editCourseBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                editCourseBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(addBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(editCourseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(173, 173, 173)
                .addComponent(removeBtn)
                .addGap(95, 95, 95))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(306, 306, 306)
                .addComponent(titleLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(335, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addBtn, editCourseBtn, removeBtn});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(titleLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                    .addComponent(removeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editCourseBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {addBtn, editCourseBtn, removeBtn});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addBtnActionPerformed
    {//GEN-HEADEREND:event_addBtnActionPerformed
        if(evt.getSource() == this.addBtn)
            try
            {
                AddEditCourseDialog acd = new AddEditCourseDialog(this);
                acd.setVisible(true);
            }
            catch(SQLException xcp)
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(this, xcp.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                xcp.printStackTrace(System.err);
            }
    }//GEN-LAST:event_addBtnActionPerformed

    private void removeBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_removeBtnActionPerformed
    {//GEN-HEADEREND:event_removeBtnActionPerformed
        if(evt.getSource() == this.removeBtn)
            try
            {
                int selectedRow = this.rowSorter.convertRowIndexToModel(this.coursesTable.getSelectedRow());
                String courseCode = (String)this.ctm.getValueAt(selectedRow, this.ctm.findColumn(CoursesTableModel.COURSE_CODE));
                int q = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the course " + courseCode + "?", "Please Confirm", JOptionPane.YES_NO_OPTION);
                if(q == JOptionPane.YES_OPTION)
                {
                    boolean deleted = CoursesDAO.removeCourse(courseCode);
                    if(deleted)
                    {
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(this, "The course " + courseCode + " has been deleted successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
                        this.refreshTable();
                    }
                }
            }
            catch(SQLException xcp)
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(this, xcp.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                xcp.printStackTrace(System.err);
            }
            catch(IndexOutOfBoundsException xcp)
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(this, "Please Select a row to delete!", "Cant Delete!", JOptionPane.WARNING_MESSAGE);
            }
    }//GEN-LAST:event_removeBtnActionPerformed

    private void coursesTableMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_coursesTableMouseMoved
    {//GEN-HEADEREND:event_coursesTableMouseMoved
        Point p = evt.getPoint();
        int rowIndex = this.coursesTable.rowAtPoint(p);
        int colIndex = this.coursesTable.columnAtPoint(p);
        String text = this.coursesTable.getValueAt(rowIndex, colIndex).toString();
        this.coursesTable.setToolTipText(text);
    }//GEN-LAST:event_coursesTableMouseMoved

    private void editCourseBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_editCourseBtnActionPerformed
    {//GEN-HEADEREND:event_editCourseBtnActionPerformed
        if(evt.getSource() == this.editCourseBtn)
            try
            {
                int selectedRow = this.rowSorter.convertRowIndexToModel(this.coursesTable.getSelectedRow());
                String courseCode = (String)this.ctm.getValueAt(selectedRow, this.ctm.findColumn(CoursesTableModel.COURSE_CODE));
                Course course = CoursesDAO.getCourse(courseCode);
                AddEditCourseDialog acd = new AddEditCourseDialog(this, course, selectedRow);
                acd.setVisible(true);
            }
            catch(SQLException | IOException | ClassNotFoundException xcp)
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(this, xcp.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                xcp.printStackTrace(System.err);
            }
            catch(IndexOutOfBoundsException xcp)
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(this, "Please Select a row to delete!", "Cant Delete!", JOptionPane.WARNING_MESSAGE);
            }
    }//GEN-LAST:event_editCourseBtnActionPerformed

    protected void selectRow(int selectedRow)
    {
        int viewRowIndex = this.coursesTable.convertRowIndexToView(selectedRow);
        this.coursesTable.changeSelection(viewRowIndex, 0, true, false);
    }

    final class IntegerComparator implements Comparator<Integer>
    {
        @Override
        public int compare(Integer i1, Integer i2)
        {
            return i1.compareTo(i2);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JTable coursesTable;
    private javax.swing.JButton editCourseBtn;
    private javax.swing.JButton removeBtn;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JLabel titleLbl;
    // End of variables declaration//GEN-END:variables

}
