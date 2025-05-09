import React, {useState, useEffect} from 'react';
import axios from 'axios';

function HQRequestPopup({isOpen, onClose}) {
  const [warehouses, setWarehouses] = useState([]);
  const [parts, setParts] = useState([]);
  const [selectedWarehouse, setSelectedWarehouse] = useState('');
  const [selectedPart, setSelectedPart] = useState('');
  const [quantity, setQuantity] = useState('');
  const [partsList, setPartsList] = useState([]);

  // 물류센터 목록 가져오기
  useEffect(() => {
    if (isOpen) {
      axios.get('http://localhost:8080/HQstatus/warehouses')
        .then((response) => {
          setWarehouses(response.data);
        })
        .catch((error) => {
          console.error('물류센터 목록을 가져오는 데 실패했습니다.', error);
        });
    }
  }, [isOpen]);

  // 선택한 물류센터의 부품 목록 가져오기
  useEffect(() => {
    if (selectedWarehouse) {
      axios.get('http://localhost:8080/HQstatus/parts', {
        headers: {
          'Warehouse-Id': selectedWarehouse  // 헤더에 selectedWarehouse 값 전달
        }
      })
        .then((response) => {
          setParts(response.data);
        })
        .catch((error) => {
          console.error(`${selectedWarehouse}에 해당하는 부품 목록을 가져오는 데 실패했습니다.`, error);
        });
    } else {
      setParts([]);
    }
  }, [selectedWarehouse]);


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
      const partInfo = parts.find((p) => p.partId === selectedPart);
      if (!partInfo) return;

      const newPart = {
        id: Date.now(),
        warehouseId: selectedWarehouse,
        partId: selectedPart,
        inboundQuantity: parseInt(quantity),
        inboundPrice: parseInt(partInfo.partPrice),

        warehouseName: warehouses.find(w => w.warehouseId === selectedWarehouse)?.warehouseName,
        partName: partInfo.partName,
      };

      setPartsList([...partsList, newPart]);
      setSelectedPart('');
      setQuantity('');
    }
  };

  const removePart = (id) => {
    setPartsList(partsList.filter((item) => item.id !== id));
  };

  const totalAmount = partsList.reduce((sum, item) => sum + item.inboundPrice * item.inboundQuantity, 0);

  // 본사 요청 API 호출
  const handleSubmit = () => {
    if (partsList.length === 0) {
      alert('요청할 부품을 추가하세요.');
      return;
    }

    // 서버에 필요한 필드만 추려서 전송
    const requestData = partsList.map(({warehouseId, partId, inboundQuantity, inboundPrice}) => ({
      warehouseId,
      partId,
      inboundQuantity,
      inboundPrice,
    }));

    axios.post('http://localhost:8080/HQstatus/request', requestData)
      .then((response) => {
        console.log('본사 요청이 성공적으로 처리되었습니다.', response.data);
        alert('본사 요청 완료!');
        closePopup();
      })
      .catch((error) => {
        console.error('본사 요청 처리 중 오류가 발생했습니다.', error);
        alert('본사 요청에 실패했습니다.');
      });
  };

  if (!isOpen) return null;

  return (
    <>
      <div
        className="modal-backdrop fade show"
        onClick={(e) => {
          if (e.target === e.currentTarget) closePopup();
        }}
      />
      <div
        className="modal fade show"
        tabIndex="-1"
        role="dialog"
        style={{
          display: 'block',
          position: 'fixed',
          top: '60%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
          zIndex: 1050,
        }}
      >
        <div className="modal-dialog modal-lg" role="document">
          <div className="modal-content">
            <div className="modal-header" style={{backgroundColor: '#cfe2ff'}}>
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
                      <option key={w.warehouseId} value={w.warehouseId}>
                        {w.warehouseName}
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
                    disabled={!selectedWarehouse}
                  >
                    <option value="">선택하세요</option>
                    {parts.map((p) => (
                      <option key={p.partId} value={p.partId}>
                        {p.partName}
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
                    disabled={!selectedWarehouse || !selectedPart || quantity <= 0}
                  >
                    추가
                  </button>
                </div>
              </form>

              {/* 부품 목록 테이블 */}
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
                        <td>{item.inboundQuantity}</td>
                        <td className="d-flex justify-content-end align-items-center p-1">
                          <span>{(item.inboundPrice * item.inboundQuantity).toLocaleString()}원</span>
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

              {/* 총합계 */}
              {partsList.length > 0 && (
                <div className="text-end fw-bold mt-3 me-2">
                  총 합계: {totalAmount.toLocaleString()}원
                </div>
              )}
            </div>
            <div className="modal-footer d-flex justify-content-center" style={{borderTop: 'none'}}>
              <button type="button" className="btn btn-outline-danger" onClick={closePopup}>
                취소
              </button>
              <button
                type="button"
                className="btn btn-outline-primary"
                onClick={handleSubmit}
                disabled={partsList.length === 0}
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
