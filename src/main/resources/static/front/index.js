var mysql      = require('mysql');
var pool = mysql.createPool({
    host     : 'localhost',
    user     : 'hotpot',
    password : 'hotpot',
    database : 'hotpot'
});

var GROUP_TYPE = {
    PEOPLE: 0,
    PERSON: 1
};
var SCORE_UNIT = {
    0: 2, // people
    1: 1  // person
};
var DEFAULT_SCORE = {
    0: SCORE_UNIT[0] * 3,
    1: SCORE_UNIT[1] * 3
};

var getNeedUpdateTaskMemberDay = `SELECT A.id AS member_day_id, A.user_id, B.total_people, B.type, B.total_task
	FROM task_member_day AS A, task_group AS B 
	WHERE A.group_id = B.id AND 
		A.finished_task = B.total_task AND 
		A.current_day = ?`;
var updateMemberDayScore = `UPDATE task_member_day
	SET score = CEIL(((SELECT IFNULL(sum(score), 0)
	    FROM score
	    WHERE to_user_id = ? AND member_day_id = ?) * ? + 
	    (? - (SELECT IFNULL(count(*), 0)
	    FROM score
	    WHERE to_user_id = ? AND member_day_id = ?)) * ?) * ?)
	WHERE id = ?`;
var updateUserPeopleScore = `UPDATE user
	SET people_score = people_score + 
	    (SELECT score
	    FROM task_member_day
	    WHERE id = ?)
	WHERE id = ?`;
var updateUserPersonScore = `UPDATE user
	SET person_score = person_score + 
	    (SELECT score
	    FROM task_member_day
	    WHERE id = ?)
	WHERE id = ?`;

var todayNoon = getTodayNoon();

pool.getConnection((err, connection) => {
    connection.query(getNeedUpdateTaskMemberDay, todayNoon)
        .on('result', function (row) {
            updateMemberDay(row)
                .then((result) => {
                    if (result.affectedRows > 0) {
                        var userId = row.user_id;
                        var memberDayId = row.member_day_id;
                        if (row.type === GROUP_TYPE.PEOPLE) {
                            updateUserPeople(userId, memberDayId);
                        } else if (row.type === GROUP_TYPE.PERSON) {
                            updateUserPerson(userId, memberDayId);
                        }
                    }
                })
                .catch(e => console.log(e))
        })
        .on('error', function (e) {
            console.log(e);
        })
        .on('end', function () {
            connection.release();
        });
})

// 获得完成当天任务的人及任务组的信息
function getUpdateTaskMemberDay() {
    return new Promise((resolved, rejected) => {
        pool.getConnection((err, connection) => {
            connection.query(getNeedUpdateTaskMemberDay, todayNoon, function (error, results, fields) {
                if (error) {
                    rejected(error);
                    return;
                }
                resolved(results);
            });
            connection.release();
        })
    })
}

// 更新某人在某任务组在某天的得分情况
function updateMemberDay(data) {
    var totalPeople = data.total_people;
    var userId = data.user_id;
    var memberDayId = data.member_day_id;
    var totalTask = data.total_task;
    var type = data.type;

    return new Promise((resolved, rejected) => {
        var multiple = getMultiple(totalTask);
        pool.getConnection((err, connection) => {
            connection.query(updateMemberDayScore,
                [userId, memberDayId, SCORE_UNIT[type], totalPeople, userId, memberDayId, DEFAULT_SCORE[type], multiple, memberDayId],
                function (err, result) {
                    if (err) {
                        rejected(err);
                        return ;
                    }
                    resolved(result);
                });
            connection.release();
        })
    })
}

// 更新多人积分
function updateUserPeople(userId, memberDayId) {
    pool.getConnection((err, connection) => {
        connection.query(updateUserPeopleScore, [memberDayId, userId], function(err, result) {
            if (err) console.log(err);
        })

        connection.release();
    })
}

// 更新个人积分
function updateUserPerson(userId, memberDayId) {
    pool.getConnection((err, connection) => {
        connection.query(updateUserPersonScore, [memberDayId, userId], function (err, result) {
            if (err) console.log(err);
        })

        connection.release();
    })
}

function getTodayNoon() {
    var now = new Date();
    return new Date(new Date(now.toDateString()).setHours(12));
}

function getMultiple(data) {
    return Math.pow(1.1, data-1);
}