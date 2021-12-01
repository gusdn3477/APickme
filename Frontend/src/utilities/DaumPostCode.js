import { useState } from 'react';
import DaumPostCode from 'react-daum-postcode';

const DaumPost = ({data}) => {

    const [address, setAddress] = useState(data);
    const handleComplete = (data) => {
        let fullAddress = data.address;
        let extraAddress = '';
        if (data.addressType === 'R') {
            if (data.bname !== '') {
                extraAddress += data.bname;
            }
            if (data.buildingName !== '') {
                extraAddress += (extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName);
            }
            fullAddress += (extraAddress !== '' ? ` (${extraAddress})` : '');
            setAddress(fullAddress);
        }
        //fullAddress -> 전체 주소반환
    }
    return (<DaumPostCode onComplete={handleComplete} className="post-code" />);
}
export default DaumPost;