package ItemManager.layout;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import ItemManager.db.ItemDAO;
import market.ItemDTO;

public class ItemJListLayout extends JFrame {

	private int width = 350;
	private int height = 350;
	private JList<ItemDTO> listItme;
	private BufferedImage image;
	private JPanel panel;
	private JButton button;
//	private JFileChooserLayout chooserLayout;

	// File Path ---
	private JFileChooser chooser = new JFileChooser();
	// 확장자 필터
	private FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF & PNG Images", "jpg", "gif", "png");
	private String imagePath;
	private File fileUrl;
	private ItemDAO dao;

	public ItemJListLayout() {
		initData();
		setInitLayout();

	}

	private void initData() {
		button = new JButton();
//		chooserLayout = new JFileChooserLayout();
	}

	private void setInitLayout() {
		add(createMainPanel());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	private JList<ItemDTO> createList() {

		DefaultListModel<ItemDTO> model = new DefaultListModel<>();
		// TODO system.in 으로 값받기 item_name, price, 이미지(borwserBox)
		// ? ? ?
		// SELECT username, price, image FROM item
		// resultset !isLast
//		model.addElement(new ItemDTO("C/C++ Programming", "A", "cpp"));
//		model.addElement(new ItemDTO("Java Programming", "B", "java"));
//		model.addElement(new ItemDTO("C# Programming", "C", "cs"));
//		model.addElement(new ItemDTO("IOS Programming", "D", "ios"));
//		model.addElement(new ItemDTO("Windows Phone Programming", "E", "wp"));
//		model.addElement(new ItemDTO("Android Programming", "F", "android"));
		JList<ItemDTO> list = new JList<ItemDTO>(model);
		list.setCellRenderer(new ItemRendererPanel());
		return list;
	}

	private JPanel createMainPanel() {
		panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.add(new JScrollPane(listItme = createList()), BorderLayout.CENTER);
		panel.add(button.add(printFilePath()), BorderLayout.SOUTH);
		return panel;
	}

	public Component printFilePath() {

		JButton button = new JButton("파일 열기");
		dao = new ItemDAO(this);
		setVisible(true); // 보이게 할지 여부
		setLayout(new FlowLayout()); // 레이아웃 변경
		add(button); // 컨테이너에 버튼 추가

		button.addActionListener(new ActionListener() { // 익명 객체
			@Override
			public void actionPerformed(ActionEvent e) {
				chooser.setFileFilter(filter); // 파일 필터 추가
				int returnVal = chooser.showOpenDialog(getParent());
				// 창 열기 정상 수행시 0 반환, 취소시 1 반환

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					fileUrl = chooser.getSelectedFile();
					imagePath = fileUrl.toString();
					System.out.println(fileUrl.toString());
					try {
						dao.addItemImage();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		return button;
	}

	private class ItemRendererPanel extends JPanel implements ListCellRenderer<ItemDTO> {

		private JLabel lbIcon = new JLabel();
		private JLabel lbName = new JLabel();
		private JLabel lbAuthor = new JLabel();
		private JPanel panelText;
		private JPanel panelIcon;
		private BufferedImage image;
		private JFileChooserLayout chooserLayout;

		public ItemRendererPanel() {
			setLayout(new BorderLayout(5, 5));

			JPanel panelText = new JPanel(new GridLayout(0, 1));
			panelText.add(lbName);
			// price
			panelText.add(lbAuthor);
			add(lbIcon, BorderLayout.WEST);
			add(panelText, BorderLayout.CENTER);
		}

		@Override
		public Component getListCellRendererComponent(JList<? extends ItemDTO> list, ItemDTO value, int index,
				boolean isSelected, boolean cellHasFocus) {
//			lbIcon.setIcon(
//					new ImageIcon(getClass().getResource("/ItemManager/images/" + ItemDTO.getIconName() + ".jpg"))); // resultSet.getImage
//			lbName.setText(ItemDTO.getPrice_name); // resultSet.getName
//			lbAuthor.setText(ItemDTO.getPirce()); // resultSet.getPrice

			// 선택시 배경 불투명도 true로 해둬야 선택을 확인가능
			lbIcon.setOpaque(true);
			lbAuthor.setOpaque(true);
			lbName.setOpaque(true);

			// 선택 체크
			if (isSelected) {
				lbName.setBackground(list.getSelectionBackground());
				lbAuthor.setBackground(list.getSelectionBackground());
				lbIcon.setBackground(list.getSelectionBackground());
				setBackground(list.getSelectionBackground());
			} else {
				lbName.setBackground(list.getBackground());
				lbAuthor.setBackground(list.getBackground());
				lbIcon.setBackground(list.getBackground());
				setBackground(list.getBackground());
			}

			return this;
		}

	}
}