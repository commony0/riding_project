<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
    <title>Cycling Club</title>
    <link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>
    <link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
</head>
<body>
    <c:import url="/menu/top.do" />

    <div class='title_line'> 가장 가까운 코스 추천받기</div>

    <div id="map" style="width:800px;height:600px; margin: 0 auto;"></div>
    <button style="display: block; margin:20px auto; " class="btn btn-secondary btn btn-sm" onclick="removeOtherMarkers()">가장 가까운 코스 선택</button>

    <script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=95d0f90e62e9cff662f12e8bc53594ce"></script>
    <script>
        var mapContainer = document.getElementById('map'), 
            mapOption = { 
                center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
                level: 7 // 지도의 확대 레벨 
            }; 
        var map = new kakao.maps.Map(mapContainer, mapOption);

        var currentPositionMarker; // 내 현재 위치 마커
        var courseMarkers = []; // 코스 마커 배열

         // 가장 가까운 코스 표시 함수 호출
        displayCourseMarkers();

        // Geolocation API를 사용하여 현재 위치를 얻어옴
        navigator.geolocation.getCurrentPosition(function (position) {
            var lat = position.coords.latitude; // 위도
            var lng = position.coords.longitude; // 경도

            // 사용자의 현재 위치를 중심으로 지도를 설정
            var currentPosition = new kakao.maps.LatLng(lat, lng);
            map.setCenter(currentPosition);
            
            // 내 현재 위치 마커 표시
            currentPositionMarker = new kakao.maps.Marker({
                position: currentPosition,
                map: map
            });

            var iwContent = '<div style="padding:5px;">나의 위치</div>'; 
            var infowindow = new kakao.maps.InfoWindow({
                content: iwContent,
                removable: true
            });
            infowindow.open(map, currentPositionMarker);

            
        });

        // 가장 가까운 코스 표시 함수
        function displayCourseMarkers() {
            // 코스 시작 지점 배열 (예시)
            var courseStartPoints = [
                // 경기도
                { lat: 37.867826, lng: 126.753327 }, // 평화누리길 8코스 반구정길 반구정
                { lat: 37.381209, lng: 126.938500 }, // 안양천 자전거길 호계대교
                { lat: 37.547356, lng: 127.243958 }, // 남양주 자전거길 팔당역
                { lat: 37.404280, lng: 127.536182 }, //  여주 남한강자전거길(여주구간) 이포보
                { lat: 37.698478, lng: 126.662627 } , // 평화누리 자전거길 2코스 행주대교
                { lat: 37.781879, lng: 127.467065 }, // 북한강자전거길 색현터널 카페 플로레
                { lat: 37.386316, lng: 126.826622 }, // 그린웨이 월미교

                // 서울
                { lat: 37.553850, lng: 126.878160 }, // 한강 한바퀴 코스 안양천 합수부
                { lat: 37.556969, lng: 126.603824 }, // 아라 자전거길 아라서해갑문
                { lat: 37.507774, lng: 126.992835 }, // 남북 코스 반포한강공원
                { lat: 37.544549, lng: 127.044592 }, // 서울숲 코스 서울숲역 5번 출구
                { lat: 37.528522, lng: 126.965839 }, // 이촌 한강공원 코스 용산역 맞은편
                { lat: 37.634047, lng: 127.059025 }, // 경춘선 숲길 코스 월계역 1번 출구

                // 인천
                { lat: 37.457981, lng: 126.756315 }, // 인천대공원 - 소래포구
                { lat: 37.440523, lng: 126.703810 }, // 승기천 자전거길 구월농산물도매시장
                { lat: 37.403062, lng: 126.648788 }, // 송도 달빛공원 둘레길 달빛공원
                { lat: 37.573806, lng: 126.755805 }, // 굴포천 자전거길 굴포천1교
                { lat: 37.392834, lng: 126.634991 }, // 송도 공원 순회 센트럴파크역
                { lat: 37.573157, lng: 126.744980 } // 자전거로 이음길 경인아라뱃길
            ];

            // 모든 코스 시작 지점을 확인하면서 마커 표시
            for (var i = 0; i < courseStartPoints.length; i++) {
                var coursePosition = new kakao.maps.LatLng(courseStartPoints[i].lat, courseStartPoints[i].lng);
                var courseMarker = new kakao.maps.Marker({
                    position: coursePosition,
                    map: map
                });
                var courseInfoTexts = [
                    // 경기도
                    '평화누리길 8코스 반구정길',
                    '안양천 자전거길',
                    '남양주 자전거길',
                    '여주 남한강자전거길',
                    '평화누리 자전거길 2코스',
                    '북한강자전거길 색현터널',
                    '그린웨이',
                    //서울
                    '한강 한바퀴 코스',
                    '아라 자전거길',
                    '남북 코스',
                    '서울숲 코스',
                    '이촌 한강공원 코스',
                    '경춘선 숲길 코스',
                    //인천
                    '인천대공원 - 소래포구',
                    '승기천 자전거길',
                    '송도 달빛공원 둘레길',
                    '굴포천 자전거길',
                    '송도 공원 순회',
                    '자전거로 이음길'  
                ];

                var iwContent = '<div style="padding:5px;">' + courseInfoTexts[i] + '</div>'; 
                var infowindow = new kakao.maps.InfoWindow({
                    content: iwContent,
                    removable: true
                });
                courseMarker.infowindow = infowindow; // 마커에 Infowindow 속성 추가

                 // 코스 마커 배열에 추가
                courseMarkers.push(courseMarker);
                
                // 클릭 이벤트 리스너 추가
                kakao.maps.event.addListener(courseMarker, 'click', function() {
                    // 클릭된 마커의 인덱스 가져오기
                    var markerIndex = courseMarkers.indexOf(this);

                    // 해당 코스의 정보 가져오기
                    var courseInfo = getCourseInfo(markerIndex);

                    // 게시글로 이동
                    if (courseInfo && courseInfo.postLink) {
                        window.location.href = courseInfo.postLink;
                    } else {
                        alert('해당 코스의 정보를 가져오지 못했습니다.');
                    }
                });
                

             // Infowindow를 마커에 연결하여 지도에 표시
                infowindow.open(map, courseMarkers[i]);
                
            }
        }

        // 가장 가까운 코스 선택 버튼 클릭 시 호출되는 함수
        function removeOtherMarkers() {
            // 현재 위치를 기준으로 가장 가까운 코스 찾기
            var nearestCourseIndex = findNearestCourse();
            
            // 가장 가까운 코스 이외의 모든 코스 마커 및 텍스트 제거
            for (var i = 0; i < courseMarkers.length; i++) {
                if (i !== nearestCourseIndex) {
                    courseMarkers[i].setMap(null);
                 // 해당 코스에 연결된 Infowindow가 있다면 닫기
                    courseMarkers[i].infowindow.close();

                }
            }
        }

        // 가장 가까운 코스 찾기 함수
        function findNearestCourse() {
            var nearestCourseIndex = -1;
            var nearestDistance = Number.MAX_VALUE;

            for (var i = 0; i < courseMarkers.length; i++) {
                var distance = calculateDistance(currentPositionMarker.getPosition(), courseMarkers[i].getPosition());

                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestCourseIndex = i;
                }
            }

            return nearestCourseIndex;
        }

        // 두 지점 간의 거리 계산 함수
        function calculateDistance(p1, p2) {
            var lat1 = p1.getLat();
            var lng1 = p1.getLng();
            var lat2 = p2.getLat();
            var lng2 = p2.getLng();

            var R = 6371; // 지구 반경 (km)
            var dLat = deg2rad(lat2 - lat1);
            var dLng = deg2rad(lng2 - lng1);
            var a =
                Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                Math.sin(dLng / 2) * Math.sin(dLng / 2);
            var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            var distance = R * c;

            return distance;
        }

        function deg2rad(deg) {
            return deg * (Math.PI / 180);
        }

        // 각 코스의 정보를 가져오는 함수 (예시: 수정이 필요함)
        function getCourseInfo(courseIndex) {
            var courseInfoArray = [
                // 경기도
                { postLink: '/detail/read.do?detailno=6&word=&now_page=2&regionno=4', title: '평화누리길 8코스 반구정길' },
                { postLink: '/detail/read.do?detailno=4&word=&now_page=3&regionno=4', title: '안양천 자전거길' },
                { postLink: '/detail/read.do?detailno=5&word=&now_page=2&regionno=4', title: '남양주 자전거길' },
                { postLink: '/detail/read.do?detailno=7&word=&now_page=2&regionno=4', title: '여주 남한강자전거길' },
                { postLink: '/detail/read.do?detailno=8&word=&now_page=1&regionno=4', title: '평화누리 자전거길 2코스' },
                { postLink: '/detail/read.do?detailno=9&word=&now_page=1&regionno=4', title: '북한강자전거길 색현터널' },
                { postLink: '/detail/read.do?detailno=22&word=&now_page=1&regionno=4', title: '그린웨이' },
                // 서울
                { postLink: '/detail/read.do?detailno=10&word=&now_page=2&regionno=1', title: '한강 한바퀴 코스' },
                { postLink: '/detail/read.do?detailno=11&word=&now_page=2&regionno=1', title: '아라 자전거길' },
                { postLink: '/detail/read.do?detailno=13&word=&now_page=2&regionno=1', title: '남북 코스' },
                { postLink: '/detail/read.do?detailno=14&word=&now_page=1&regionno=1', title: '서울숲 코스' },
                { postLink: '/detail/read.do?detailno=15&word=&now_page=1&regionno=1', title: '이촌 한강공원 코스' },
                { postLink: '/detail/read.do?detailno=16&word=&now_page=1&regionno=1', title: '경춘선 숲길 코스' },
                // 인천
                { postLink: '/detail/read.do?detailno=12&word=&now_page=2&regionno=3', title: '인천대공원 - 소래포구' },
                { postLink: '/detail/read.do?detailno=17&word=&now_page=2&regionno=3', title: '승기천 자전거길' },
                { postLink: '/detail/read.do?detailno=18&word=&now_page=2&regionno=3', title: '송도 달빛공원 둘레길' },
                { postLink: '/detail/read.do?detailno=19&word=&now_page=1&regionno=3', title: '굴포천 자전거길' },
                { postLink: '/detail/read.do?detailno=20&word=&now_page=1&regionno=3', title: '송도 공원 순회' },
                { postLink: '/detail/read.do?detailno=21&word=&now_page=1&regionno=3', title: '자전거로 이음길' }

            ];

            return courseInfoArray[courseIndex];
        }
    </script>

    <jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
</html>