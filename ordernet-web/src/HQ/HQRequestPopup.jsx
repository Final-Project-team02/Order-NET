import React, { useState } from 'react';

function HQRequestPopup({ isOpen, onClose }) {
  const [selectedWarehouse, setSelectedWarehouse] = useState('');
  const [selectedPart, setSelectedPart] = useState('');
  const [quantity, setQuantity] = useState('');
  const [partsList, setPartsList] = useState([]);

  const warehouses = [
    { id: 'w1', name: '서울 물류센터' },
    { id: 'w2', name: '부산 물류센터' },
  ];
  const parts = [
    { id: 'p1', name: '엔진 오일', price: 50000 },
    { id: 'p2', name: '에어필터', price: 20000 },
    { id: 'p3', name: '브레이크 패드', price: 80000 },
  ];

  const closePopup = () => {
    onClose();
    resetForm();
  };

  const resetForm = () => {
    setSelectedWarehouse('');
    setSelectedPart('');
    setQuantity('');
    setPartsList([]);
  };

  const addPart = () => {
    if (selectedWarehouse && selectedPart && quantity > 0) {
      const partInfo = parts.find((p) => p.id === selectedPart);
      const warehouseInfo = warehouses.find((w) => w.id === selectedWarehouse);

      const newPart = {
        id: Date.now(),
        warehouseName: warehouseInfo?.name,
        partName: partInfo?.name,
        quantity: parseInt(quantity),
        price: partInfo?.price,
      };

      setPartsList([...partsList, newPart]);
      setSelectedPart('');
      setQuantity('');
    }
  };

  const removePart = (id) => {
    setPartsList(partsList.filter((item) => item.id !== id));
  };

  const totalAmount = partsList.reduce((sum, item) => sum + item.price * item.quantity, 0);

  if (!isOpen) return null;

  return (
      <>
        <div
            className="modal-backdrop fade show"
            onClick={(e) => { if (e.target === e.currentTarget) closePopup(); }}
        />
        <div
            className="modal fade show"
            tabIndex="-1"
            role="dialog"
            style={{
              display: 'block',
              position: 'fixed',
              top: '50%',
              left: '50%',
              transform: 'translate(-50%, -50%)',
              zIndex: 1050,
            }}
        >
          <div className="modal-dialog modal-lg" role="document">
            <div className="modal-content">
              <div className="modal-header" style={{ backgroundColor: '#cfe2ff' }}>
                <h5 className="modal-title w-100 text-center"><b>본사 요청</b></h5>
              </div>
              <div className="modal-body">
                <form>
                  <div className="mb-3">
                    <label className="form-label fw-bold">물류센터</label>
                    <select
                        className="form-select"
                        value={selectedWarehouse}
                        onChange={(e) => setSelectedWarehouse(e.target.value)}
                    >
                      <option value="">선택하세요</option>
                      {warehouses.map((w) => (
                          <option key={w.id} value={w.id}>
                            {w.name}
                          </option>
                      ))}
                    </select>
                  </div>

                  <div className="mb-3">
                    <label className="form-label fw-bold">부품 선택</label>
                    <select
                        className="form-select"
                        value={selectedPart}
                        onChange={(e) => setSelectedPart(e.target.value)}
                    >
                      <option value="">선택하세요</option>
                      {parts.map((p) => (
                          <option key={p.id} value={p.id}>
                            {p.name}
                          </option>
                      ))}
                    </select>
                  </div>

                  <div className="mb-3">
                    <label className="form-label fw-bold">수량</label>
                    <input
                        type="number"
                        className="form-control"
                        min="1"
                        value={quantity}
                        onChange={(e) => setQuantity(e.target.value)}
                        placeholder="수량 입력"
                    />
                  </div>

                  <div className="text-end mb-3">
                    <button
                        type="button"
                        className="btn btn-outline-primary"
                        onClick={addPart}
                    >
                      추가
                    </button>
                  </div>
                </form>

                {/* 부품 목록 */}
                <div className="table-responsive">
                  <table className="table table-bordered text-center align-middle">
                    <thead className="table-light">
                    <tr>
                      <th>센터</th>
                      <th>부품</th>
                      <th>수량</th>
                      <th>가격</th>
                    </tr>
                    </thead>
                    <tbody>
                    {partsList.length > 0 ? (
                        partsList.map((item) => (
                            <tr key={item.id}>
                              <td>{item.warehouseName}</td>
                              <td>{item.partName}</td>
                              <td>{item.quantity}</td>
                              <td className="d-flex justify-content-end align-items-center p-1">
                                <span>{(item.price * item.quantity).toLocaleString()}원</span>
                                <button
                                    className="btn btn-sm btn-outline-danger ms-2"
                                    onClick={() => removePart(item.id)}
                                >
                                  ✕
                                </button>
                              </td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                          <td colSpan="4" className="text-muted">
                            추가된 부품이 없습니다.
                          </td>
                        </tr>
                    )}
                    </tbody>
                  </table>
                </div>

                {/* 합계 */}
                {partsList.length > 0 && (
                    <div className="text-end fw-bold mt-3 me-2">
                      총 합계: {totalAmount.toLocaleString()}원
                    </div>
                )}
              </div>
              <div className="modal-footer d-flex justify-content-center" style={{ borderTop: 'none' }}>
                <button type="button" className="btn btn-outline-danger" onClick={closePopup}>
                  취소
                </button>
                <button
                    type="button"
                    className="btn btn-outline-primary"
                    onClick={() => {
                      console.log(partsList);
                      alert('본사 요청 완료!');
                      closePopup();
                    }}
                >
                  신청
                </button>
              </div>
            </div>
          </div>
        </div>
      </>
  );
}

export default HQRequestPopup;
