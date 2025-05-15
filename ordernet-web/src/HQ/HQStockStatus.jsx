import HQSidebarMenu from "./HQSidebarMenu.jsx";
import HQTopbar from "./HQTopbar.jsx";
import React, {useState} from "react";
import HQRequestPopup from "./HQRequestPopup.jsx";
import PartInsertPopUp from "./HQPartInsertPopUp.jsx";
import Title from "../layout/Title.jsx";

function HQStockStatus() {

    const menuItems = [
        {text: "주문 확정", link: "/"},
        {text: "대리점 관리", link: "/HQClientList"},
        {text: "재고현황", link: "/HQStockStatus"}
    ];

    const items = [
        {Id: 'ABC-1234', mpm: '한국PIM', Mname: '브레이크 패드', MCode: 'A2345', cate: '엔진', Int: '125'},
        {Id: 'ABC-1234', mpm: '한국PIM', Mname: '브레이크 패드', MCode: 'A2245', cate: '엔진', Int: '100'},
        {Id: 'ABC-1234', mpm: '한국PIM', Mname: '브레이크 패드', MCode: 'A2315', cate: '엔진', Int: '111'},
        {Id: 'ABC-1234', mpm: '한국PIM', Mname: '브레이크 패드', MCode: 'A005', cate: '엔진', Int: '166'},
        {Id: 'ABC-1234', mpm: '한국PIM', Mname: '브레이크 패드', MCode: 'A9445', cate: '엔진', Int: '11'},
        {Id: 'ABC-1234', mpm: '한국PIM', Mname: '브레이크 패드', MCode: 'A2005', cate: '엔진', Int: '124'}
    ]
    // 입고 요청 팝업 관리
    const [isRequestPopupOpen, setIsRequestPopupOpen] = useState(false);
    // 부품 등록 팝업 관리
    const [isPartInsertPopupOpen, setIsPartInsertPopupOpen] = useState(false);

    return (
        <div className="d-flex vh-100">
            <HQSidebarMenu menuItems={menuItems}/>
            <div className="flex-grow-1 d-flex flex-column overflow-hidden">
                <HQTopbar title="재고 현황"/>
                <div className="p-3 overflow-auto" style={{height: "calc(100vh - 120px)"}}>

                    <Title breadcrumb= "☆ 물류 관리 > 재고 현황" panelTitle="재고 현황"/>

                    <div className={'mt-4 p-3 bg-light d-flex'}>
                        <div className={'d-flex'}>
                            <ul className={'list-unstyled d-flex mx-5 mb-0 align-items-center'}>
                                <li className={'d-inline px-3'}>
                                    <label htmlFor="">카테고리</label>
                                    <input className={'mx-2'} style={{border: "none"}} type="text"/>
                                </li>
                                <li className={'d-inline px-3'}>
                                    <label htmlFor="">부품 명</label>
                                    <input className={'mx-2'} style={{border: "none"}} type="text"/>
                                </li>
                            </ul>
                            <button className={'btn'} style={{backgroundColor: "#CFE2FF"}} type={"button"}>조회
                            </button>
                        </div>
                        <div className={'ms-auto me-5'}>
                            <button className={"btn me-2"} style={{backgroundColor: "#CFE2FF"}}
                                    onClick={() => setIsRequestPopupOpen(true)}>
                                입고
                            </button>
                            <HQRequestPopup isOpen={isRequestPopupOpen} onClose={() => setIsRequestPopupOpen(false)}/>

                            <button className={"btn me-2"} style={{backgroundColor: "#CFE2FF"}}
                                    onClick={() => setIsPartInsertPopupOpen(true)}>
                                부품 등록
                            </button>
                            <PartInsertPopUp isOpen={isPartInsertPopupOpen} onClose={() => setIsPartInsertPopupOpen(false)}/>
                        </div>
                    </div>
                    <div style={{
                        height: '50vh',
                        display: 'flex',
                        justifyContent: 'center',
                        flexDirection: 'column'
                    }}>
                        <table className={'table table-bordered text-center'}>
                            <thead style={{borderBottom: '1.5px solid #000'}}>
                            <tr style={{border: "none"}}>
                                <th style={{border: "none"}}>부품고유 ID</th>
                                <th style={{border: "none"}}>물류 센터</th>
                                <th style={{border: "none"}}>부품명</th>
                                <th style={{border: "none"}}>부품 코드번호</th>
                                <th style={{border: "none"}}>부품 카테고리</th>
                                <th style={{border: "none"}}>수량</th>
                            </tr>
                            </thead>
                            <tbody>
                            {items.map((datas, t) => (
                                <tr style={{border: "none"}} key={t}>
                                    <td style={{border: "none"}}>{datas.Id}</td>
                                    <td style={{border: "none"}}>{datas.mpm}</td>
                                    <td style={{border: "none"}}>{datas.Mname}</td>
                                    <td style={{border: "none"}}>{datas.MCode}</td>
                                    <td style={{border: "none"}}>{datas.cate}</td>
                                    <td style={{border: "none"}}>{datas.Int}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default HQStockStatus

