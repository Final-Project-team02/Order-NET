import React, { useState, useEffect } from "react";
import axios from "axios";
import HQSidebarMenu from "./HQSidebarMenu.jsx";
import HQTopbar from "./HQTopbar.jsx";
import Title from "../layout/Title.jsx";
import HQRequestPopup from "./HQRequestPopup.jsx";
import PartInsertPopUp from "./HQPartInsertPopUp.jsx";
import {SortAscIcon} from "lucide-react";

function HQStockStatus() {
  const menuItems = [
    { text: "본사 대시보드 ", link: "/" },
    { text: "주문 확정", link: "/HQMain" },
    { text: "대리점 관리", link: "/HQClientList" },
    { text: "재고현황", link: "/HQStockStatus" },
  ];

  const [items, setItems] = useState([]);
  const [filteredItems, setFilteredItems] = useState([]);
  const [isRequestPopupOpen, setIsRequestPopupOpen] = useState(false);
  const [isPartInsertPopupOpen, setIsPartInsertPopupOpen] = useState(false);
  const [partName, setPartName] = useState("");
  const [partCate, setPartCate] = useState("");
  const [sortOrder, setSortOrder] = useState(null);

  // 페이지네이션 상태
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage, setItemsPerPage] = useState(10);

  useEffect(() => {
    axios.get('http://localhost:8080/HQstatus')
      .then(response => {
        const data = response.data;
        if (Array.isArray(data)) {
          setItems(data);
          setFilteredItems(data);
        } else {
          setItems([]);
          setFilteredItems([]);
        }
      })
      .catch(error => {
        console.error("Error fetching data:", error);
        setItems([]);
        setFilteredItems([]);
      });
  }, []);

  const handleSearch = () => {
    const filtered = items.filter(item => {
      const matchesPartName = partName ? item.partName.toLowerCase().includes(partName.toLowerCase()) : true;
      const matchesPartCate = partCate ? item.partCate.toLowerCase().includes(partCate.toLowerCase()) : true;
      return matchesPartName && matchesPartCate;
    });
    setFilteredItems(filtered);
    setCurrentPage(1);
  };

  const totalPages = Math.ceil(filteredItems.length / itemsPerPage);

  // 현재 페이지에 맞는 항목들 자르기
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = filteredItems.slice(indexOfFirstItem, indexOfLastItem);

  // 페이지네이션 표시 범위 계산
  const maxPageNumbers = 5;
  const pageGroup = Math.floor((currentPage - 1) / maxPageNumbers);
  let startPage = pageGroup * maxPageNumbers + 1;
  let endPage = Math.min(startPage + maxPageNumbers - 1, totalPages);
  if (totalPages <= maxPageNumbers) {
    startPage = 1;
    endPage = totalPages;
  }

  const renderPageNumbers = () => {
    const pageNumbers = [];
    for (let i = startPage; i <= endPage; i++) {
      pageNumbers.push(
        <li key={i} className={`page-item ${i === currentPage ? "active" : ""}`}>
          <button className="page-link" onClick={() => setCurrentPage(i)}>{i}</button>
        </li>
      );
    }
    return pageNumbers;
  };

  const SortByQuantity = () =>{
    let newSortOrder = 'asc';
    if (sortOrder === 'asc') newSortOrder = 'desc';

    setSortOrder(newSortOrder);

    let sortedItems = [...filteredItems];
    if (newSortOrder === 'asc') {
      sortedItems.sort((a, b) => a.stockQuantity - b.stockQuantity);
    } else if (newSortOrder === 'desc') {
      sortedItems.sort((a, b) => b.stockQuantity - a.stockQuantity);
    }
    setFilteredItems(sortedItems);
  }


  // const headerStyle = { backgroundColor: "#CFE2FF" };

  return (
    <div className="d-flex vh-100">
      <HQSidebarMenu menuItems={menuItems} />
      <div className="flex-grow-1 d-flex flex-column overflow-hidden">
        <HQTopbar title="재고 현황" />
        <div className="p-3 overflow-auto" style={{ height: "calc(100vh - 120px)" }}>
          <Title breadcrumb="☆ 본사 > 물류 관리" panelTitle="재고 현황" />

          {/* 필터 */}
          <div className="mt-4 p-3 bg-secondary bg-opacity-10 d-flex" style={{flexWrap: 'nowrap', overflowX: 'auto'}}>
            <div className="d-flex">
              <ul className="list-unstyled d-flex mx-5 mb-0 align-items-center gap-3">
                <li className="d-inline">
                  <label className="d-flex align-items-center">
                    <span className="fw-bold me-2" style={{whiteSpace: 'nowrap'}}>● 부품 카테고리</span>
                    <input
                      className="form-control flex-shrink-1"
                      style={{minWidth: '200px', border: 'none'}}
                      type="text"
                      value={partCate}
                      onChange={(e) => setPartCate(e.target.value)} // 부품 카테고리 입력 상태 관리
                    />
                  </label>
                </li>
                <li className="d-inline">
                  <label className="d-flex align-items-center">
                    <span className="fw-bold me-2" style={{whiteSpace: 'nowrap'}}>● 부품 명</span>
                    <input
                      className="form-control flex-shrink-1"
                      style={{minWidth: '200px', border: 'none'}}
                      type="text"
                      value={partName}
                      onChange={(e) => setPartName(e.target.value)} // 부품명 입력 상태 관리
                    />
                  </label>
                </li>
              </ul>
              <button className="btn" style={{backgroundColor: "#CFE2FF"}} type="button" onClick={handleSearch}>
                조회
              </button>
            </div>

            <div className="ms-auto me-5 d-flex align-items-center gap-3">
              <button
                className="btn"
                style={{backgroundColor: "#CFE2FF"}}
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
                style={{backgroundColor: "#CFE2FF"}}
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

          {/* 표시 개수 선택 */}
          <div className="mt-3 mb-2 me-4 d-flex justify-content-end">
            <label className="d-flex align-items-center">
              <span className="me-2">항목 수 :</span>
              <select
                className="form-select"
                style={{width: "80px"}}
                value={itemsPerPage}
                onChange={(e) => {
                  const value = e.target.value === "전체보기" ? filteredItems.length : Number(e.target.value);
                  setItemsPerPage(value);
                  setCurrentPage(1);
                }}
              >
                <option value="전체보기">전체보기</option>
                <option value={10}>10</option>
                <option value={20}>20</option>
                <option value={30}>30</option>
              </select>
            </label>
          </div>

          <hr/>
          {/* 테이블 */}
          <div className="p-4 mt-2 bg-light w-100 overflow-auto">
            <table className="table table-bordered" style={{tableLayout: "fixed"}}>
              <thead>
              <tr>
                <th className="text-center " style={{width: "15%", backgroundColor: "#CFE2FFFF"}}>부품 고유 ID</th>
                <th className="text-center" style={{width: "30%", backgroundColor: "#CFE2FFFF"}}>물류 센터</th>
                <th className="text-center" style={{width: "30%", backgroundColor: "#CFE2FFFF"}}>부품 명</th>
                <th className="text-center" style={{width: "15%", backgroundColor: "#CFE2FFFF"}}>부품 카테고리</th>
                <th className="text-center" style={{width: "15%",
                    backgroundColor: "#CFE2FFFF",cursor: "pointer",  userSelect: 'none' }}
                  onClick={SortByQuantity}>
                  <div style={{display: "inline-flex", alignItems: "center", justifyContent: "center", gap: "4px",position: "relative"}}><span>수량</span>
                    {sortOrder === "asc" && (
                      <img src="/src/assets/sort_asc.svg" alt="오름차순" />
                    )}
                    {sortOrder === "desc" && (
                      <img src="/src/assets/sort_desc.svg" alt="내림차순" />
                    )}
                    {!sortOrder && (
                      <img src="/src/assets/sort_both.svg" alt="양방향" />
                    )}
                  </div>
                </th>

              </tr>
              </thead>
              <tbody>
              {currentItems.length > 0 ? (
                currentItems.map((data, index) => (
                  <tr key={index}>
                    <td className="text-center">{data.partId}</td>
                    <td className="text-center">{data.warehouseName}</td>
                    <td className="text-center">{data.partName}</td>
                    <td className="text-center">{data.partCate}</td>
                    <td className="text-center">{data.stockQuantity}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="5" className="text-center">조회된 데이터가 없습니다.</td>
                </tr>
              )}
              </tbody>
            </table>

            {/* 페이지네이션 */}
            {totalPages > 1 && (
              <nav>
                <ul className="pagination justify-content-center mt-4">
                  <li className={`page-item ${currentPage === 1 ? "disabled" : ""}`}>
                    <button
                      className="page-link"
                      onClick={() => currentPage > 1 && setCurrentPage(currentPage - 1)}
                    >
                      &lt;
                    </button>
                  </li>
                  {renderPageNumbers()}
                  <li className={`page-item ${currentPage === totalPages ? "disabled" : ""}`}>
                    <button
                      className="page-link"
                      onClick={() => currentPage < totalPages && setCurrentPage(currentPage + 1)}
                    >
                      &gt;
                    </button>
                  </li>
                </ul>
              </nav>
            )}

          </div>
        </div>
      </div>
    </div>
  );
}

export default HQStockStatus;
