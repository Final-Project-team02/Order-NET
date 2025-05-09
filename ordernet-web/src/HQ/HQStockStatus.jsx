import React, { useState, useEffect } from "react";
import axios from "axios";
import HQSidebarMenu from "./HQSidebarMenu.jsx";
import HQTopbar from "./HQTopbar.jsx";
import Title from "../layout/Title.jsx";
import HQRequestPopup from "./HQRequestPopup.jsx";
import PartInsertPopUp from "./HQPartInsertPopUp.jsx";

function HQStockStatus() {
  const menuItems = [
    { text: "주문 확정", link: "/" },
    { text: "대리점 관리", link: "/HQClientList" },
    { text: "재고현황", link: "/HQStockStatus" }
  ];

  // 서버에서 받아오는 데이터 상태 관리
  const [items, setItems] = useState([]);
  const [filteredItems, setFilteredItems] = useState([]); // 필터링된 항목 상태
  const [isRequestPopupOpen, setIsRequestPopupOpen] = useState(false);
  const [isPartInsertPopupOpen, setIsPartInsertPopupOpen] = useState(false);

  // 필터 상태 관리
  const [partName, setPartName] = useState(""); // 부품명
  const [partCate, setPartCate] = useState(""); // 부품 카테고리

  // 표 머릿말 스타일
  const headerStyle = {
    backgroundColor: "#CFE2FF",
  };

  useEffect(() => {
    axios.get('http://localhost:8080/HQstatus')
      .then(response => {
        const data = response.data;
        console.log('서버 응답:', data);

        if (Array.isArray(data)) {
          setItems(data);  // 전체 데이터 저장
          setFilteredItems(data);  // 초기에는 전체 데이터를 필터링된 목록으로 설정
        } else {
          console.warn('서버에서 배열이 아닌 데이터가 반환되었습니다:', data);
          setItems([]); // 빈 배열로 fallback
          setFilteredItems([]); // 빈 배열로 fallback
        }
      })
      .catch(error => {
        console.error("Error fetching data:", error);
        setItems([]); // 오류 발생 시 빈 배열
        setFilteredItems([]); // 오류 발생 시 빈 배열
      });
  }, []);

  // 필터링 함수
  const handleSearch = () => {
    const filtered = items.filter(item => {
      const matchesPartName = partName ? item.partName.toLowerCase().includes(partName.toLowerCase()) : true;
      const matchesPartCate = partCate ? item.partCate.toLowerCase().includes(partCate.toLowerCase()) : true;

      return matchesPartName && matchesPartCate;
    });

    setFilteredItems(filtered); // 필터링된 데이터를 상태에 저장
  };

  return (
    <div className="d-flex vh-100">
      <HQSidebarMenu menuItems={menuItems} />
      <div className="flex-grow-1 d-flex flex-column overflow-hidden">
        <HQTopbar title="재고 현황" />
        <div className="p-3 overflow-auto" style={{ height: "calc(100vh - 120px)" }}>
          <Title breadcrumb="☆ 물류 관리 > 재고 현황" panelTitle="재고 현황" />

          <div className="mt-4 p-3 bg-secondary bg-opacity-10 d-flex" style={{ flexWrap: 'nowrap', overflowX: 'auto' }}>
            <div className="d-flex">
              <ul className="list-unstyled d-flex mx-5 mb-0 align-items-center gap-3">
                <li className="d-inline">
                  <label className="d-flex align-items-center">
                    <span className="fw-bold me-2" style={{ whiteSpace: 'nowrap' }}>● 부품 카테고리</span>
                    <input
                      className="form-control flex-shrink-1"
                      style={{ minWidth: '200px', border: 'none' }}
                      type="text"
                      value={partCate}
                      onChange={(e) => setPartCate(e.target.value)} // 부품 카테고리 입력 상태 관리
                    />
                  </label>
                </li>
                <li className="d-inline">
                  <label className="d-flex align-items-center">
                    <span className="fw-bold me-2" style={{ whiteSpace: 'nowrap' }}>● 부품 명</span>
                    <input
                      className="form-control flex-shrink-1"
                      style={{ minWidth: '200px', border: 'none' }}
                      type="text"
                      value={partName}
                      onChange={(e) => setPartName(e.target.value)} // 부품명 입력 상태 관리
                    />
                  </label>
                </li>
              </ul>
              <button className="btn" style={{ backgroundColor: "#CFE2FF" }} type="button" onClick={handleSearch}>
                조회
              </button>
            </div>

            <div className="ms-auto me-5 d-flex align-items-center gap-3">
              <button
                className="btn"
                style={{ backgroundColor: "#CFE2FF" }}
                onClick={() => setIsRequestPopupOpen(true)} // 함수 형태로 전달
              >
                입고
              </button>
              <HQRequestPopup
                isOpen={isRequestPopupOpen}
                onClose={() => setIsRequestPopupOpen(false)}
              />

              <button
                className="btn"
                style={{ backgroundColor: "#CFE2FF" }}
                onClick={() => setIsPartInsertPopupOpen(true)} // 함수 형태로 전달
              >
                부품 등록
              </button>
              <PartInsertPopUp
                isOpen={isPartInsertPopupOpen}
                onClose={() => setIsPartInsertPopupOpen(false)}
              />
            </div>
          </div>

          {/* 테이블 영역 */}
          <div className="p-4 mt-5 bg-light w-100 overflow-auto">
            <table className="table table-bordered">
              <thead>
              <tr>
                <th className="text-center align-middle" style={headerStyle}>부품 고유 ID</th>
                <th className="text-center align-middle" style={headerStyle}>물류 센터</th>
                <th className="text-center align-middle" style={headerStyle}>부품 명</th>
                <th className="text-center align-middle" style={headerStyle}>부품 카테고리</th>
                <th className="text-center align-middle" style={headerStyle}>수량</th>
              </tr>
              </thead>

              <tbody>
              {filteredItems.length ? (
                filteredItems.map((data, index) => (
                  <tr key={index}>
                    <td className="text-center align-middle">{data.partId}</td>
                    <td className="text-center align-middle">{data.warehouseName}</td>
                    <td className="text-center align-middle">{data.partName}</td>
                    <td className="text-center align-middle">{data.partCate}</td>
                    <td className="text-center align-middle">{data.stockQuantity}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="5" className="text-center">조회된 데이터가 없습니다.</td>
                </tr>
              )}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
}

export default HQStockStatus;
