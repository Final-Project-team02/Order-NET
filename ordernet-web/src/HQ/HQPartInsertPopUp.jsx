import React, { useState, useEffect } from 'react';
import axios from 'axios';

function PartInsertPopUp({ isOpen, onClose }) {
  const [formData, setFormData] = useState({
    warehouseId: '',
    warehouseName: '',
    warehouseCate: '',
    partCate: '',
    partName: '',
    quantity: '',
    partImageUrl: '',
    partPrice: '',
  });

  const [warehouses, setWarehouses] = useState([]);
  const [warehouseCates, setWarehouseCates] = useState([]);
  const [partCates, setPartCates] = useState([]);

  // 물류센터 목록 요청
  useEffect(() => {
    if (!isOpen) return;

    axios.get('http://localhost:8080/HQstatus/warehouses')
      .then((response) => {
        setWarehouses(response.data);
      })
      .catch((err) => {
        console.error('물류센터 조회 실패:', err);
      });
  }, [isOpen]);

  // 물류센터명 선택 시 warehouseId 설정
  useEffect(() => {
    const selectedWarehouse = warehouses.find(w => w.warehouseName === formData.warehouseName);
    if (selectedWarehouse) {
      setFormData(prev => ({
        ...prev,
        warehouseId: selectedWarehouse.warehouseId, // warehouseId를 설정
      }));
    }
  }, [formData.warehouseName, warehouses]);  // warehouseName이나 warehouses가 변경될 때마다 실행

  // 물류센터명 선택 시 카테고리 요청
  useEffect(() => {
    if (formData.warehouseName === '') return;

    axios.get(`http://localhost:8080/HQstatus/warehouse-cate?warehouseName=${encodeURIComponent(formData.warehouseName)}`)
      .then((response) => {
        setWarehouseCates(response.data);

        // 만약 물류센터 카테고리가 하나라면 자동으로 선택하고 disabled 처리
        if (response.data.length === 1) {
          setFormData(prev => ({
            ...prev,
            warehouseCate: response.data[0]  // 카테고리 자동 설정
          }));
        }
      })
      .catch((err) => {
        console.error('물류센터 카테고리 조회 실패:', err);
      });
  }, [formData.warehouseName]);

  // 물류센터 카테고리 선택 시 부품 카테고리 요청
  useEffect(() => {
    if (formData.warehouseCate === '') return;

    axios.get(`http://localhost:8080/HQstatus/part-cate?warehouseId=${encodeURIComponent(formData.warehouseId)}`)
      .then((response) => {
        // 중복 제거 처리
        const uniquePartCates = [...new Set(response.data)];
        setPartCates(uniquePartCates);

        // 부품 카테고리가 하나일 경우, 자동으로 선택하고 disabled 처리
        if (uniquePartCates.length === 1) {
          setFormData(prev => ({
            ...prev,
            partCate: uniquePartCates[0]  // 부품 카테고리 자동 설정
          }));
        }
      })
      .catch((err) => {
        console.error('부품 카테고리 조회 실패:', err);
      });
  }, [formData.warehouseId]);



  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    axios.post('http://localhost:8080/HQstatus/insert', formData)
      .then((response) => {
        alert(response.data); // 성공 메시지 표시
        onClose();  // 팝업 닫기
      })
      .catch((err) => {
        alert('부품 등록에 실패했습니다.' + (err.response ? err.response.data : err.message));
      });
  };

  if (!isOpen) return null;

  return (
    <>
      <div className="modal-backdrop fade show" onClick={(e) => { if (e.target === e.currentTarget) onClose(); }} />

      <div className="modal fade show" tabIndex="-1" role="dialog" style={{ display: 'block', position: 'fixed', top: '50%', left: '50%', zIndex: 1050, transform: 'translate(-50%, -50%)' }}>
        <div className="modal-dialog modal-fullscreen-md-down" role="document">
          <div className="modal-content">
            <div className="modal-header d-flex justify-content-center" style={{ backgroundColor: '#cfe2ff' }}>
              <h5 className="modal-title text-center"><b>부품 등록</b></h5>
            </div>
            <div className="modal-body">
              <form onSubmit={handleSubmit}>
                <div className="mb-3">
                  <select className="form-select" name="warehouseName" value={formData.warehouseName} onChange={handleChange}>
                    <option value="">물류센터 선택</option>
                    {warehouses.map(w => <option key={w.warehouseId} value={w.warehouseName}>{w.warehouseName}</option>)}
                  </select>
                </div>

                <div className="mb-3">
                  <select className="form-select" name="warehouseCate" value={formData.warehouseCate} onChange={handleChange}  disabled={warehouseCates.length === 1}>
                    <option value="">물류센터 카테고리 선택</option>
                    {warehouseCates.map((cate, idx) => <option key={idx} value={cate}>{cate}</option>)}
                  </select>
                </div>

                <div className="mb-3">
                  <select className="form-select" name="partCate" value={formData.partCate} onChange={handleChange} disabled={partCates.length === 1}>
                    <option value="">부품 카테고리 선택</option>
                    {partCates.map((cate, idx) => <option key={idx} value={cate}>{cate}</option>)}
                  </select>
                </div>

                <div className="mb-3">
                  <input type="text" className="form-control" placeholder="부품명" name="partName" value={formData.partName} onChange={handleChange} />
                </div>

                <div className="mb-3">
                  <input type="number" className="form-control" placeholder="수량" name="quantity" value={formData.quantity} onChange={handleChange} />
                </div>

                <div className="mb-3">
                  <input type="text" className="form-control" placeholder="이미지 주소(URL)" name="partImageUrl" value={formData.partImageUrl} onChange={handleChange} />
                </div>

                <div className="mb-3">
                  <input type="number" className="form-control" placeholder="부품 가격" name="partPrice" value={formData.partPrice} onChange={handleChange} />
                </div>

                <div className="modal-footer d-flex justify-content-center" style={{ borderTop: 'none' }}>
                  <button type="button" className="btn btn-outline-danger me-2" onClick={onClose}>취소</button>
                  <button type="submit" className="btn btn-outline-primary">등록</button>
                </div>

              </form>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default PartInsertPopUp;
