package bitc.fullstack503.ordernetserver.service;

import bitc.fullstack503.ordernetserver.dto.WHDTO;
import bitc.fullstack503.ordernetserver.mapper.WHMapper;
import bitc.fullstack503.ordernetserver.mapper.WarehouseStockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WHServiceImpl implements WHService {

    @Autowired
    private WHMapper whMapper;

    @Autowired
    private WarehouseStockMapper warehouseStockMapper;

    //    물류센터 입고조회
    @Override
    public List<WHDTO> selectWHComeIn(String userId) {
        return whMapper.selectWHComeIn(userId);
    }
    //    물류센터 재고조회
    @Override
    public List<WHDTO> selectWHStock(String userId) {
        return whMapper.selectWHStock(userId);
    }

    //    재고관리 조회
    @Override
    public List<WHDTO> selectWHManage(String userId) {
        return whMapper.selectWHManage(userId);
    }

    @Override
    @Transactional
    public void updateOrderStatus(int orderItemId, String newStatus, String partId, int orderItemQuantity) {
        String currentStatus = whMapper.selectOrderItemStatus(orderItemId);
        whMapper.updateOrderStatus(orderItemId, newStatus);

        // 출고대기 → 출고완료
        if ("출고대기".equals(currentStatus) && "출고완료".equals(newStatus)) {
            Integer currentStock = warehouseStockMapper.selectStockQuantity(partId);
            if (currentStock == null) throw new RuntimeException("재고 정보가 없습니다: partId = " + partId);
            if (currentStock < orderItemQuantity) throw new RuntimeException("재고 부족");
            int updatedRows = warehouseStockMapper.deleteStock(partId, orderItemQuantity);
            if (updatedRows == 0) throw new RuntimeException("재고 차감 실패");
        }

        // 출고완료 → 출고대기
        else if ("출고완료".equals(currentStatus) && "출고대기".equals(newStatus)) {
            warehouseStockMapper.restoreStock(partId, orderItemQuantity);
        }

        // 주문 상태 처리
        String orderId = whMapper.selectOrderIdByItemId(orderItemId);
        int notCompleted = whMapper.countNonShippedItems(orderId);

        if (notCompleted == 0) {
            whMapper.updateOrderToShipped(orderId);

            // 출고이력 중복 방지 후 INSERT
            String warehouseId = whMapper.selectWarehouseIdByOrderId(orderId);
            boolean alreadyExists = whMapper.existsOutbound(warehouseId, orderId); //중복 방지

            if (!alreadyExists) {
                whMapper.insertOutbound(warehouseId, orderId);  // outbound_date = NOW()
            }
        }
    }

    //    출고관리 필터링
    @Override
    public List<WHDTO> selectWHManageFiltered(String userId, String orderItemStatus, String branchName,
                                              String orderId, String orderStartDate, String orderEndDate) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("orderItemStatus", orderItemStatus);
        paramMap.put("branchName", branchName);
        paramMap.put("orderId", orderId);
        paramMap.put("orderStartDate", orderStartDate);
        paramMap.put("orderEndDate", orderEndDate);

        return whMapper.selectWHManageFiltered(paramMap); // Mapper에서 필터링된 데이터 반환
    }

    }



