import {useEffect, useState} from "react";
import axios from "axios";

function HQBranchMap() {

  // 대리점 정보 가져오기
  const [branchData, setBranchData] = useState([]);

  const getBranchMap = () =>{
    axios
        .get('http://localhost:8080/HQDashBoard/BranchPos')
        .then(res=>{
          console.log("서버에서 받은 데이터:", res.data);
          setBranchData(res.data);
        })
        .catch(err=>{
          console.log("error가 나왔습니다.");
          console.log(err);
        });
  };
  useEffect(() => {
    getBranchMap();
  }, []);

  return (
      <div>
        {/* 서버에서 받아온 대리점 정보 출력 */}
        <h1>대리점 목록</h1>
        <ul>
          {branchData.length > 0 ? (
              branchData.map((branch, index) => (
                  <li key={index}>
                    {/* 대리점 정보 출력*/}
                    <strong>{branch.branchName}</strong> {branch.branchSupervisor + " "}
                    {branch.branchPhone+ " "} {branch.branchZipCode+ " "} {branch.branchRoadAddr}
                  </li>
              ))
          ) : (
              <p>대리점 정보가 없습니다.</p>
          )}
        </ul>
      </div>
  );
}

export default HQBranchMap