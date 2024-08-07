

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import lombok.Data;
import market.MainFrame;
import market.DTO.ItemListDTO;
import market.repoIm.ItemRepoImpl;

@Data
class ItemListPanel extends JPanel implements ListSelectionListener {

	private MainFrame mContext;
	private PanelAdapter panelAdapter;

	private JList<ItemListDTO> listItemDTO;
	private DefaultListModel<ItemListDTO> model;
	private ItemRepoImpl itemRepoImpl;
	private List<ItemListDTO> itemListDTOs;
	private JPanel jPanel;
	private JScrollPane jsPane;
	private int product_id;
	ItemDetilPanel itemDetilPanel;

	private int currentPage = 0;
	private static final int PAGE_SIZE = 20;

	public ItemListPanel(MainFrame mContext, PanelAdapter panelAdapter) {
		this.mContext = mContext;
		this.panelAdapter = panelAdapter;
		this.itemRepoImpl = mContext.getItemRepoImpl();
		initData();
		setInitLayout();
		loadItems(currentPage);

		jsPane.getVerticalScrollBar().addAdjustmentListener(e -> {
			if (!e.getValueIsAdjusting()
					&& e.getValue() + e.getAdjustable().getVisibleAmount() >= e.getAdjustable().getMaximum()) {
				currentPage++;
				loadItems(currentPage);
			}
		});

		listItemDTO.addListSelectionListener(this);
	}

	// 한번에 데이터를 불러오니 시간이 오래걸림
	// 해결법 - 지연로딩-Lazy Loading(데이터베이스에서 한번에 데이터를 불러와 일부만 보여준후 스크롤을 내리면 추가로 보여주는 방식)
	private void loadItems(int page) {
		// 데이터베이스에서 1만개 이상의 데이터를 한번에 받아오기 때문에 시간이 오래걸림
		// 해결법 - SwingWorker 사용하여 백그라운드에서 데이터 로드
		new SwingWorker<List<ItemListDTO>, Void>() {
			long startTime;

			// 리스트에 띄우는 곳
			@Override
			protected void done() {
				long endTime = System.currentTimeMillis();
				try {
					List<ItemListDTO> items = get();
					for (ItemListDTO itemListDTO : items) {
						model.addElement(itemListDTO);
					}
					listItemDTO.setCellRenderer(new ItemListDTORenderer());
					System.out.println("Items loaded in: " + (endTime - startTime) + " ms");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// 백그라운드에서 데이터베이스를 데이터를 요청하는 곳
			@Override
			protected List<ItemListDTO> doInBackground() throws Exception {
				startTime = System.currentTimeMillis();
				return itemRepoImpl.getItemDTO(page * PAGE_SIZE, PAGE_SIZE);
			}
		}.execute();
	}

	public void upDateList() {
		model.clear();
		currentPage = 0;
		loadItems(currentPage);
	}

	private void initData() {
		setLayout(null);
		setSize(400, 500);
		setLocation(0, 50);
		setVisible(true);
	}

	private void setInitLayout() {
		add(jsPane = new JScrollPane(listItemDTO = createListItemDTO()));
		jsPane.setSize(400, 500);
		jsPane.setLocation(0, 0);
	}

	private JList<ItemListDTO> createListItemDTO() {
		model = new DefaultListModel<>();
		return new JList<ItemListDTO>(model);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			ItemListDTO selectedItem = listItemDTO.getSelectedValue();
			if (selectedItem != null) {
				product_id = selectedItem.getProductId();
				itemDetilPanel = new ItemDetilPanel(mContext.getMyUserDTO(), product_id, itemRepoImpl, panelAdapter);
				panelAdapter.addItemDetailPanel(itemDetilPanel);
			}
		}
	}

	@Data
	private class ItemListDTORenderer extends JPanel implements ListCellRenderer<ItemListDTO> {

		private JLabel lbIcon = new JLabel();
		private JLabel lbName = new JLabel();
		private JLabel lbPrice = new JLabel();
		private JPanel panelText;
		private JPanel panelIcon;
		private ImageIcon icon = null;

		public ItemListDTORenderer() {
			setLayout(new BorderLayout(5, 5));

			panelText = new JPanel(new GridLayout(0, 1));
			panelText.add(lbName);
			panelText.add(lbPrice);

			panelIcon = new JPanel();
			panelIcon.setBorder(new EmptyBorder(0, 0, 0, 0));
			panelIcon.add(lbIcon);

			add(panelIcon, BorderLayout.WEST);
			add(panelText, BorderLayout.CENTER);
		}

		@Override
		public Component getListCellRendererComponent(JList<? extends ItemListDTO> list, ItemListDTO itemListDTO,
				int index, boolean isSelected, boolean cellHasFocus) {
			try {
				icon = byteArrayToImageIcon(itemListDTO.getImage());
			} catch (IOException e) {
				e.printStackTrace();
				icon = new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)); // 기본 아이콘
			}

			lbIcon.setIcon(icon);
			lbName.setText(itemListDTO.getProductName());

			lbPrice.setText(itemListDTO.getPrice());
			lbPrice.setForeground(Color.blue);

			lbName.setOpaque(true);
			lbPrice.setOpaque(true);
			lbIcon.setOpaque(true);

			if (isSelected) {
				lbName.setBackground(list.getSelectionBackground());
				lbPrice.setBackground(list.getSelectionBackground());
				lbIcon.setBackground(list.getSelectionBackground());
				setBackground(list.getSelectionBackground());
			} else {
				lbName.setBackground(list.getBackground());
				lbPrice.setBackground(list.getBackground());
				lbIcon.setBackground(list.getBackground());
				setBackground(list.getBackground());
			}
			return this;
		}
	}

	public ImageIcon byteArrayToImageIcon(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		BufferedImage bImage = ImageIO.read(bis);
		bis.close();
		Image image = bImage.getScaledInstance(bImage.getWidth(), bImage.getHeight(), Image.SCALE_DEFAULT);
		return new ImageIcon(image);
	}
}
