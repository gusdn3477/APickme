import React, {useState} from 'react';
import Banner from '../../elements/ui/Banner';
import Header from '../../layout/Header';
import Footer from '../../layout/Footer';
import AdvancedTable from '../../elements/widgets/Home/AdvancedTable';
import AdvancedTable2 from '../../elements/widgets/Home/AdvancedTable2';

export default function Interview() {

  const [data, setData] = useState({
    title: "공고 리스트",
    name: "공고명",
    companyName: "회사명",
    period: "공고 기간",
    status: "공고 현황"
  });

  return (
    <div id="wrap">
      <Header />
      <div className="container-scroller">
        <div className="container-fluid page-body-wrapper">
          {/* Banner는 왼쪽에 있는 리스트 형식의 메뉴 */}
          <Banner />
          
          {/* 여기부터 프사 누르면 나오는 메뉴 */}
          <div className="main-panel">  
            <div className="content-wrapper">
              {/* <div className="row">
                <TopProducts data={data}/>
              </div> */}
              <div className="row">
                <AdvancedTable/>
              </div>
              <div className="row">
                <AdvancedTable2/>
              </div>
            </div>
            <Footer />
          </div>
        </div>
      </div>
    </div>

  );
}