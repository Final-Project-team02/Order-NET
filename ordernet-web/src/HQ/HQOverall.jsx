import HQBranchMap from "./HQBranchMap.jsx";
import HQNoticeList from "./HQNoticeList.jsx";

function HQOverall() {
    return (
        <div>
            <div style={{ display: 'flex', gap: '8px', alignItems: 'flex-start' }}>
                <div style={{ width: '600px' }}>
                    <HQNoticeList />
                </div>
                <div style={{ width: '480px' }}>
                    <HQBranchMap />
                </div>
            </div>

        </div>
    );
}

export default HQOverall
