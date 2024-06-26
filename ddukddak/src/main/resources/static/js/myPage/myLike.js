let currentPage = 1;
const housePageSize = 10; // 집들이 페이지당 게시글 수
const tipPageSize = 12; // 노하우 페이지당 게시글 수

function onload() {
    loadHouseBoards(currentPage, housePageSize);

    document.querySelector(".select-box").addEventListener("change", function() {
        const boardCode = this.value;
        currentPage = 1;
        if (boardCode == 1) {
            loadHouseBoards(currentPage, housePageSize);
        } else {
            loadTipBoards(currentPage, tipPageSize);
        }
    });
}

function updatePagination(pagination) {
    const paginationElement = document.getElementById("pagination");
    if (!paginationElement) return; // pagination 요소가 없으면 종료

    paginationElement.innerHTML = '';
    console.log(pagination.maxPage);
   //<,<<
    const firstPage = document.createElement("li");

    if (currentPage === 1) {
        firstPage.classList.add("disabled");
        firstPage.innerHTML = `&lt;&lt;`;
    }else{
        firstPage.innerHTML = `<a href="#" onclick="goToPage(1)">&lt;&lt;</a>`;
    }
    paginationElement.appendChild(firstPage);

    const prevPage = document.createElement("li");

    if (currentPage === 1) {
        prevPage.classList.add("disabled");
        prevPage.innerHTML= `&lt;`;
    }else{
        prevPage.innerHTML = `<a href="#" onclick="goToPage(${currentPage - 1})">&lt;</a>`;
    }
    paginationElement.appendChild(prevPage);


    for (let i = pagination.startPage; i <= pagination.endPage; i++) {
        const page = document.createElement("li");
        if (i === currentPage) {
            page.innerHTML = `<a class="current" style="font-weight: bold;">${i}</a>`;
        } else {
            page.innerHTML = `<a href="#" onclick="goToPage(${i})">${i}</a>`;
        }
        paginationElement.appendChild(page);
    }
    // >,>>
    const nextPage = document.createElement("li");

    if (currentPage === pagination.maxPage) {
        console.log(pagination.maxPage);
        nextPage.classList.add("disabled");
        nextPage.innerHTML=`&gt;`;
    }else{
        nextPage.innerHTML = `<a href="#" onclick="goToPage(${currentPage + 1})">&gt;</a>`;
    }
    paginationElement.appendChild(nextPage);

    const lastPage = document.createElement("li");

    if (currentPage === pagination.maxPage) {
        lastPage.classList.add("disabled");
        lastPage.innerHTML = `&gt;`;
    }else{
        lastPage.innerHTML = `<a href="#" onclick="goToPage(${pagination.maxPage})">&gt;&gt;</a>`;
    }
    paginationElement.appendChild(lastPage);
    
}

function goToPage(page) {
    currentPage = page;
    const boardCode = document.querySelector(".select-box").value;
    if (boardCode == 1) {
        loadHouseBoards(currentPage, housePageSize);
    } else {
        loadTipBoards(currentPage, tipPageSize);
    }
}

function loadHouseBoards(page, pageSize) {
    fetch(`/myCommunity/House?cp=${page}&size=${pageSize}`)
        .then(resp => resp.json())
        .then(data => {
            if (data) {
                const boardList = data.likeHouseBoardList || [];
                const pagination = data.pagination;

                const postArea = document.querySelector("#postArea");
                postArea.innerHTML = "";

                if (boardList.length > 0) {
                    const ul = document.createElement("ul");
                    ul.classList.add("homeBoard-list");

                    boardList.forEach((board) => {
                        const li = document.createElement("li");
                        li.classList.add("myHouseBoardList");

                        const img = document.createElement("img");
                        img.classList.add("homeBoard-img");
                        img.src = board.thumbnail;

                        const h3 = document.createElement("h3");
                        h3.classList.add("homeboard-title");
                        h3.innerHTML = board.boardTitle;
                        h3.href = `myHouse/detail/${board.boardNo}`;

                        li.append(img, h3);

                        const div1 = document.createElement("div");
                        div1.classList.add("homeBoard-info");

                        const div2 = document.createElement("div");
                        const a2 = document.createElement("a");
                        a2.innerText = "방문하기>";
                        a2.href = `/myHouse/detail/${board.boardNo}`;
                        div2.append(a2);

                        const div3 = document.createElement("div");
                        const span1 = document.createElement("span");
                        const span2 = document.createElement("span");
                        const span3 = document.createElement("span");
                        span1.innerText = board.memberNickname;
                        span2.innerText = '조회수 ' + board.readCount;
                        span3.innerText = board.boardUpdateDate != null ? board.boardUpdateDate : board.boardWriteDate;

                        div3.append(span1, span2, span3);
                        div1.append(div2, div3);
                        li.append(div1);

                        ul.append(li);
                    });

                    postArea.append(ul);
                    updatePagination(pagination);
                } else {
                    const div = document.createElement("div");
                    div.classList.add("noHouseData");
                    div.innerText = "좋아요 한 게시글이 존재하지 않습니다.";
                    postArea.append(div);
                }
            }
        });
}

function loadTipBoards(page, pageSize) {
    fetch(`/myCommunity/Tip?cp=${page}&size=${pageSize}`)
        .then(resp => resp.json())
        .then(data => {
            if (data) {
                const myTipBoardList = data.likeTipBoardList || [];
                const pagination = data.pagination;

                const postArea = document.querySelector("#postArea");
                postArea.innerHTML = "";

                if (myTipBoardList.length > 0) {
                    const table = document.createElement('table');
                    table.className = 'tipBoard-table';
                    const tbody = document.createElement('tbody');
                    postArea.append(table);
                    table.appendChild(tbody);

                    const rows = Math.ceil(myTipBoardList.length / 3);

                    for (let rowIndex = 0; rowIndex < rows; rowIndex++) {
                        const tr = document.createElement('tr');
                        tr.className = 'tipBoard-row';

                        for (let colIndex = 0; colIndex < 3; colIndex++) {
                            const listIndex = rowIndex * 3 + colIndex;

                            if (listIndex < myTipBoardList.length) {
                                const td = document.createElement('td');
                                const section = document.createElement('section');
                                section.className = 'tipBoard-item';

                                const imgA = document.createElement("a");
                                imgA.href = "/tip/deatil/" + myTipBoardList[listIndex].boardNo;
                                const img = document.createElement('img');
                                img.className = 'tipBoard-img';
                                img.src = myTipBoardList[listIndex].thumbnail;

                                imgA.append(img);
                                section.appendChild(imgA);

                                const userSpan = document.createElement('span');
                                userSpan.textContent = '@' + myTipBoardList[listIndex].memberNickname;
                                section.appendChild(userSpan);

                                const titleSpan = document.createElement('span');
                                titleSpan.textContent = myTipBoardList[listIndex].boardTitle;
                                titleSpan.href = "/tip/deatil/" + myTipBoardList[listIndex].boardNo;
                                section.appendChild(titleSpan);

                                const likeSpan = document.createElement('span');
                                const likeIcon = document.createElement('i');
                                likeIcon.className = 'fa-solid fa-heart';
                                const likeCountSpan = document.createElement('span');
                                likeCountSpan.className = 'likeCount';
                                likeCountSpan.textContent = myTipBoardList[listIndex].likeCount;
                                likeSpan.appendChild(likeIcon);
                                likeSpan.appendChild(likeCountSpan);
                                section.appendChild(likeSpan);

                                const contentSpan = document.createElement('span');
                                contentSpan.textContent = myTipBoardList[listIndex].boardContent;
                                section.appendChild(contentSpan);

                                td.appendChild(section);
                                tr.appendChild(td);
                            }
                        }
                        tbody.appendChild(tr);
                    }

                    updatePagination(pagination);
                } else {
                    const div = document.createElement("div");
                    div.classList.add("noHouseData");
                    div.innerText = "좋아요 한 게시글이 존재하지 않습니다.";
                    postArea.append(div);
                }
            }
        });
}

onload();
