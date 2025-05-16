import React, {useEffect, useState} from "react";
import HQSidebarMenu from "./HQSidebarMenu.jsx";
import HQTopbar from "./HQTopbar.jsx";
import HQUpdatePopUp from "./HQUpdatePopUp.jsx";
import HQInsertPopUp from "./HQInsertPopUp.jsx";
import Title from "../layout/Title.jsx";
import { useNavigate } from "react-router-dom";
import axios from "axios";


function HQClientList() {

    const menuItems = [
        { text: "주문 확정", link: "/" },
        { text: "대리점 관리", link: "/HQClientList" },
        { text: "재고현황", link: "/HQStockStatus" },
    ];

    const [postData, setPostData] = useState([]);
    const [selectedClient, setSelectedClient] = useState([]);
    const [selectedBranchId, setSelectedBranchId] = useState(null);

    const navigate = useNavigate();
    const getPostData = () =>{
        axios
            .get('http://localhost:8080/HQMain/clientlist')
            .then(res=>{
                console.log("서버에서 받은 데이터:", res.data);
                setPostData(res.data);
            })
            .catch(err=>{
                console.log("error가 나왔습니다.");
                console.log(err);
            });
    };
    useEffect(() => {
        getPostData();
    }, []);

    // 대리점 추가 팝업 관리
    const [isInsertPopupOpen, setIsInsertPopupOpen] = useState(false);
    // 대리점 수정 팝업 관리
    const [isUpdatePopupOpen, setIsUpdatePopupOpen] = useState(false);

    // 체크박스 상태 변경
    // 체크박스 상태 처리 함수
    const handleCheckboxChange = (branchId, isChecked) => {
        // 사용자가 체크한지 확인
        if (isChecked) {
            // ..prev는 기존 배열을 복사하고, 그 뒤에 branchId를 추가하는 문법
            setSelectedClient(prev => [...prev, branchId]);
        }
        else {
            setSelectedClient(prev => prev.filter(id => id !==branchId));
        }
    };

    // 대리점 수정
    const handleUpdateClick = () => {
        if (selectedClient.length !== 1) {
            alert("수정할 대리점 1개만 선택해주세요.");
            return;
        }

        const selectedBranch = postData.find(data => data.branchId === selectedClient[0]);
        if (selectedBranch) {
            setSelectedBranchId(selectedBranch.branchId);
            setIsUpdatePopupOpen(true);
        } else {
            alert("대리점 정보를 찾을 수 없습니다.");
        }
    };


    // 선택된 대리점 삭제
    const handleDeleteSelected = () => {
        //  선택된 대리점이 없는 경우 밑에 alert 메이지가 띔
        if (selectedClient.length === 0) {
            alert("삭제할 대리점을 선택해주세요.");
            return;
        }
        console.log("삭제 요청 ID 목록:", selectedClient);

        // 선택된 대리점 ID들을 서버로 삭제 요청
        axios
            .delete('http://localhost:8080/HQMain/deleteclients', {
                data: {branchIds: selectedClient },
            })
            .then(res => {
                console.log("삭제 성공:", res.data);
                // 삭제 후 서버에서 새로운 대리점 목록을 가져오기
                // 삭제가 끝났으니, 화면을 새로고침하듯 최신 대리점 목록을 다시 가져엄
                getPostData();
                // 삭제 후에는 체크박스 선택 목록도 모두 비워줍
                setSelectedClient([]); // 삭제 후 선택된 항목 초기화
                alert("대리점이 성공적으로 삭제되었습니다.");
            })
            .catch(err => {
                console.error("삭제 실패:", err);
                alert("대리점 삭제 중 오류가 발생했습니다.");
            });
    };
    return (
        <div className="d-flex vh-100">
            <HQSidebarMenu menuItems={menuItems} />
            <div className="flex-grow-1 d-flex flex-column overflow-hidden">
                <HQTopbar title="대리점 관리" />
                <div className="p-3 overflow-auto" style={{ height: "calc(100vh - 120px)" }}>
                    <div>
                        <Title breadcrumb= "☆ 본사 > 대리점 관리" panelTitle="대리점 리스트"/>
                        <div
                            style={{
                                height: '80vh',
                                display: 'flex',
                                justifyContent: 'center',
                                flexDirection: 'column',
                            }}
                        >
                            <div className={'mt-5'}>
                                <div className={'text-end'}>
                                    <button className={"btn me-2"} style={{backgroundColor: "#CFE2FF"}}
                                            onClick={() => setIsInsertPopupOpen(true)}>
                                        추가
                                    </button>
                                    <HQInsertPopUp isOpen={isInsertPopupOpen} onClose={() => setIsInsertPopupOpen(false)}/>

                                    <button className={"btn me-2"} style={{backgroundColor: "#CFE2FF"}} onClick={handleUpdateClick}>
                                        수정
                                    </button>
                                    <HQUpdatePopUp isOpen={isUpdatePopupOpen} onClose={() => setIsUpdatePopupOpen(false)} selectedBranchId={selectedBranchId} refreshClientList={getPostData} />

                                    <button onClick={() => navigate("/HQClientRanking")} className={"btn me-2"} style={{backgroundColor: "#CFE2FF"}}>
                                        순위
                                    </button>

                                    <button className={'btn me-2'}
                                            style={{ backgroundColor: "#FFB6B3" }}
                                            onClick={handleDeleteSelected}>
                                        삭제
                                    </button>
                                </div>
                            </div>
                            <hr className={'mb-5 mt-4'}/>
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
                                <tr style={{ border: "none"}}>
                                    <th style={{ border: "none", backgroundColor:"#CFE2FF"  }}>선택</th>
                                    <th style={{ border: "none", backgroundColor:"#CFE2FF"  }}>대리점명</th>
                                    <th style={{ border: "none", backgroundColor:"#CFE2FF"  }}>대표자명</th>
                                    <th style={{ border: "none", backgroundColor:"#CFE2FF"  }}>대리점고유ID</th>
                                    <th style={{ border: "none", backgroundColor:"#CFE2FF"  }}>대리점 전화번호</th>
                                    <th style={{ border: "none", backgroundColor:"#CFE2FF"  }}>대리점 주소</th>
                                </tr>
                                </thead>
                                <tbody>
                                {postData.map((data,k) => (
                                    <tr key={k}>
                                        <td>
                                            <input
                                                type="checkbox"
                                                className={'form-check-input'}
                                                value={data.branchId}
                                                onChange={(e) =>
                                                    handleCheckboxChange(e.target.value, e.target.checked)
                                                }
                                                id={`client_${data.branchId}`}
                                            />

                                        </td>
                                        <td>{data.branchName}</td>
                                        <td>{data.branchSupervisor}</td>
                                        <td>{data.branchId}</td>
                                        <td>{data.branchPhone}</td>
                                        <td>{data.branchRoadAddr}</td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default HQClientList;
