import React, { useState } from "react";
import HQSidebarMenu from "./HQSidebarMenu.jsx";
import HQTopbar from "./HQTopbar.jsx";
import PartInsertPopUp from "./HQPartInsertPopUp.jsx";
import HQUpdatePopUp from "./HQUpdatePopUp.jsx";
import HQRequestPopup from "./HQRequestPopup.jsx";
import HQInsertPopUp from "./HQInsertPopUp.jsx";
import Title from "../layout/Title.jsx"; // Import 수정된 팝업 컴포넌트

function HQClientList() {
    const menuItems = [
        { text: "주문 확정", link: "/" },
        { text: "대리점 관리", link: "/HQClientList" },
        { text: "재고현황", link: "/HQStockStatus" },
    ];

    const data = [
        { clientName: 'Order Net', clientCeoName: '하므로', clientId: 'AD123DA', clientPhone: '010-1234-1234', clientAddr: '부산 서면'},
        { clientName: 'Order Net', clientCeoName: '경민', clientId: 'AD123DB', clientPhone: '010-1234-1234', clientAddr: '부산 서면'},
        { clientName: 'Order Net', clientCeoName: '이동윤', clientId: 'AD123DC', clientPhone: '010-1234-1234', clientAddr: '부산 서면'},
        { clientName: 'Order Net', clientCeoName: '이의진',clientId: 'AD123DA', clientPhone: '010-1234-1234', clientAddr: '부산 서면'},
        { clientName: 'Order Net', clientCeoName: '정민',clientId: 'AD123DA', clientPhone: '010-1234-1234', clientAddr: '부산 서면'},
        { clientName: 'Order Net', clientCeoName: '노종근',clientId: 'AD123DA', clientPhone: '010-1234-1234', clientAddr: '부산 서면'},
    ];
    // 대리점 추가 팝업 관리
    const [isInsertPopupOpen, setIsInsertPopupOpen] = useState(false);
    // 대리점 수정 팝업 관리
    const [isUpdatePopupOpen, setIsUpdatePopupOpen] = useState(false);

    return (
        <div className="d-flex vh-100">
            <HQSidebarMenu menuItems={menuItems} />
            <div className="flex-grow-1 d-flex flex-column overflow-hidden">
                <HQTopbar title="대리점 관리" />
                <div className="p-3 overflow-auto" style={{ height: "calc(100vh - 120px)" }}>
                    <div>
                        <Title breadcrumb= "☆ 대리점 관리 > 대리점 리스트" panelTitle="대리점 리스트"/>
                        <div
                            style={{
                                height: '80vh',
                                display: 'flex',
                                justifyContent: 'center',
                                flexDirection: 'column',
                            }}
                        >
                            <table className="table table-bordered text-center ">
                                <colgroup>
                                    <col width={'5%'}/>
                                    <col width={'15%'}/>
                                    <col width={'15%'}/>
                                    <col width={'15%'}/>
                                    <col width={'20%'}/>
                                    <col width={'30%'}/>
                                </colgroup>
                                <thead>
                                <tr style={{ border: "none" }}>
                                    <th style={{ border: "none" }}>선택</th>
                                    <th style={{ border: "none" }}>대리점명</th>
                                    <th style={{ border: "none" }}>대표자명</th>
                                    <th style={{ border: "none" }}>대리점고유ID</th>
                                    <th style={{ border: "none" }}>대리점 전화번호</th>
                                    <th style={{ border: "none" }}>대리점 주소</th>
                                </tr>
                                </thead>
                                <tbody>
                                {data.map((datas, t) => (
                                    <tr key={t}>
                                        <td>
                                            <input type="checkbox" className={'form-check-input'} value={''} id={'client_id'} />
                                        </td>
                                        <td>{datas.clientName}</td>
                                        <td>{datas.clientCeoName}</td>
                                        <td>{datas.clientId}</td>
                                        <td>{datas.clientPhone}</td>
                                        <td>{datas.clientAddr}</td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                            <div className={'mt-5'}>
                                <hr/>
                                <div className={'text-end'}>
                                    <button className={"btn me-2"} style={{backgroundColor: "#CFE2FF"}}
                                            onClick={() => setIsInsertPopupOpen(true)}>
                                        추가
                                    </button>
                                    <HQInsertPopUp isOpen={isInsertPopupOpen} onClose={() => setIsInsertPopupOpen(false)}/>

                                    <button className={"btn me-2"} style={{backgroundColor: "#CFE2FF"}}
                                            onClick={() => setIsUpdatePopupOpen(true)}>
                                        수정
                                    </button>
                                    <HQUpdatePopUp isOpen={isUpdatePopupOpen} onClose={() => setIsUpdatePopupOpen(false)}/>

                                    <button className={'btn me-2'} style={{backgroundColor: "#FFB6B3"}} type={"button"}>
                                        삭제
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default HQClientList;
