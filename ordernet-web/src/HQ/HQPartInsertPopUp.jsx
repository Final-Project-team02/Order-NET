import React, { useState, useEffect } from 'react';
import axios from 'axios';

function PartInsertPopUp({ isOpen, onClose }) {
  const [formData, setFormData] = useState({
    warehouseId: '',
    warehouseName: '',
    warehouseCate: '',
    partCate: '',
    partId: '',
    partName: '',
    stockQuantity: '',
    partImg: '',
    partPrice: '',
  });

  const [warehouses, setWarehouses] = useState([]);
  const [warehouseCates, setWarehouseCates] = useState([]);
  const [partCates, setPartCates] = useState([]);

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

  useEffect(() => {
    const selectedWarehouse = warehouses.find(w => w.warehouseName === formData.warehouseName);
    if (selectedWarehouse) {
      setFormData(prev => ({
        ...prev,
        warehouseId: selectedWarehouse.warehouseId,
      }));
    }
  }, [formData.warehouseName, warehouses]);

  useEffect(() => {
    if (formData.warehouseName === '') return;

    axios.get(`http://localhost:8080/HQstatus/warehouse-cate?warehouseName=${encodeURIComponent(formData.warehouseName)}`)
      .then((response) => {
        setWarehouseCates(response.data);

        if (response.data.length === 1) {
          setFormData(prev => ({
            ...prev,
            warehouseCate: response.data[0]
          }));
        }
      })
      .catch((err) => {
        console.error('물류센터 카테고리 조회 실패:', err);
      });
  }, [formData.warehouseName]);

  useEffect(() => {
    if (!formData.warehouseId) return;

    axios.get(`http://localhost:8080/HQstatus/part-cate?warehouseId=${encodeURIComponent(formData.warehouseId)}`)
      .then((response) => {
        const rawData = response.data;
        const uniquePartCates = [...new Set(rawData.map(item => item.partCate))];
        const uniquePartIds = [...new Set(rawData.map(item => item.next_part_id))];

        setPartCates(uniquePartCates);

        if (uniquePartCates.length === 1) {
          setFormData(prev => ({
            ...prev,
            partCate: uniquePartCates[0]
          }));
        }

        if (uniquePartIds.length === 1) {
          setFormData(prev => ({
            ...prev,
            partId: uniquePartIds[0]
          }));
        }
      })
      .catch((err) => {
        console.error('부품 카테고리 조회 실패:', err);
      });
  }, [formData.warehouseId]);

  useEffect(() => {
    if (formData.partCate === '') return;

    axios.get(`http://localhost:8080/HQstatus/part-cate?warehouseId=${encodeURIComponent(formData.warehouseId)}`)
      .then((response) => {
        const data = response.data;

        const filteredData = data.filter(item => item.partCate === formData.partCate);
        const uniquePartIds = [...new Set(filteredData.map(item => item.next_part_id))];

        if (uniquePartIds.length === 1) {
          setFormData(prev => ({
            ...prev,
            partId: uniquePartIds[0]
          }));
        }
      })
      .catch((err) => {
        console.error('부품 ID 조회 실패:', err);
      });
  }, [formData.partCate, formData.warehouseId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleClose = () => {
    // 상태를 초기화하고 모달을 닫습니다.
    setFormData({
      warehouseId: '',
      warehouseName: '',
      warehouseCate: '',
      partCate: '',
      partId: '',
      partName: '',
      stockQuantity: '',
      partImg: '',
      partPrice: '',
    });

    // 부모 컴포넌트의 onClose 함수 호출
    onClose();
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    axios.post('http://localhost:8080/HQstatus/insert', formData)
      .then((response) => {
        alert(response.data);
        handleClose();
      })
      .catch((err) => {
        alert('부품 등록에 실패했습니다.' + (err.response ? err.response.data : err.message));
        handleClose();
      });
  };

  if (!isOpen) return null;

  return (
    <>
      <div className="modal-backdrop fade show" onClick={(e) => { if (e.target === e.currentTarget) handleClose(); }} />

      <div className="modal fade show" tabIndex="-1" role="dialog" style={{ display: 'block', position: 'fixed', top: '60%', left: '50%', zIndex: 1050, transform: 'translate(-50%, -50%)' }}>


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
                  <select className="form-select" name="warehouseCate" value={formData.warehouseCate} onChange={handleChange} disabled={warehouseCates.length === 1}>
                    <option value="">물류센터 카테고리 선택</option>
                    {warehouseCates.map((cate, idx) => <option key={idx} value={cate}>{cate}</option>)}
                  </select>
                </div>

                <div className="mb-3">
                  <div className="row">
                    <div className="col-sm-6">
                      <select className="form-select" name="partCate" value={formData.partCate} onChange={handleChange} disabled={partCates.length === 1}>
                        <option value="">부품 카테고리 선택</option>
                        {partCates.map((cate, idx) => <option key={idx} value={cate}>{cate}</option>)}
                      </select>
                    </div>
                    <div className="col-sm-6">
                      <input type="text" className="form-control" placeholder="부품 ID" name="partId" value={formData.partId} disabled />
                    </div>
                  </div>
                </div>

                <div className="mb-3">
                  <input type="text" className="form-control" placeholder="부품명" name="partName" value={formData.partName} onChange={handleChange} />
                </div>

                <div className="mb-3">
                  <input type="number" className="form-control" placeholder="수량" name="stockQuantity" value={formData.stockQuantity} onChange={handleChange} />
                </div>

                <div className="mb-3">
                  <input type="text" className="form-control" placeholder="이미지 주소(URL)" name="partImg" value={formData.partImg} onChange={handleChange} />
                </div>

                <div className="mb-3">
                  <input type="number" className="form-control" placeholder="부품 가격" name="partPrice" value={formData.partPrice} onChange={handleChange} />
                </div>

                <div className="modal-footer d-flex justify-content-center" style={{ borderTop: 'none' }}>
                  <button type="button" className="btn btn-outline-danger me-2" onClick={handleClose}>취소</button>
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
