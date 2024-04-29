import json
import time
from collections import OrderedDict
from datetime import datetime

import psycopg2

volume = int(input("Specify your import volume (an int input, range: 0%-100%, standard: 20, 50, 100):\n-> "))
print("\n")
statement_counts = 0

read_start_time = time.time()


class Station:
    def __init__(self, english_name, chinese_name, district, intro):
        self.english_name = english_name
        self.chinese_name = chinese_name
        self.district = district
        self.intro = intro

    def __str__(self):
        return f"Station{{english_name={self.english_name}, chinese_name={self.chinese_name}, district={self.district}, intro={self.intro}}}"


class BusInfo:
    def __init__(self, bus_line, bus_name):
        self.bus_line = bus_line
        self.bus_name = bus_name

    def __str__(self):
        return f"BusInfo{{bus_line={self.bus_line}, bus_name={self.bus_name}}}"


class BusExitInfo:
    def __init__(self, station_name, exit, bus_info_id):
        self.station_name = station_name
        self.exit = exit
        self.bus_info_id = bus_info_id

    def __eq__(self, other):
        if not isinstance(other, BusExitInfo):
            return False
        return (self.station_name, self.exit, self.bus_info_id) == (other.station_name, other.exit, other.bus_info_id)

    def __hash__(self):
        return hash((self.station_name, self.exit, self.bus_info_id))

    def __str__(self):
        return f"BusExitInfo{{station_name={self.station_name}, exit={self.exit}, bus_info_id={self.bus_info_id}}}"


class LandmarkInfo:
    def __init__(self, landmark):
        self.landmark = landmark

    def __str__(self):
        return f"LandmarkInfo{{landmark={self.landmark}}}"


class LandmarkExitInfo:
    def __init__(self, station_name, exit, landmark_id):
        self.station_name = station_name
        self.exit = exit
        self.landmark_id = landmark_id

    def __eq__(self, other):
        if not isinstance(other, LandmarkExitInfo):
            return False
        return (self.station_name, self.exit, self.landmark_id) == (other.station_name, other.exit, other.landmark_id)

    def __hash__(self):
        return hash((self.station_name, self.exit, self.landmark_id))

    def __str__(self):
        return f"LandmarkExitInfo{{station_name={self.station_name}, exit={self.exit}, landmark_id={self.landmark_id}}}"


stations = []
bus_infos = []
bus_infos_map = {}
bus_exit_infos = []
bus_exit_infos_set = set()
landmark_infos = []
landmark_infos_map = {}
landmark_exit_infos = []
landmark_exit_infos_set = set()

with open('resources/stations.json', 'r', encoding='utf-8') as file:
    data = json.load(file)

    bus_info_id = 0
    landmark_id = 0

    for english_name, station_data in data.items():
        chinese_name = station_data['chinese_name']
        district = station_data['district']
        intro = station_data['intro']

        station = Station(english_name, chinese_name, district, intro)
        stations.append(station)

        bus_info_array = station_data.get('bus_info', [])
        for bus_info_object in bus_info_array:
            exits_in_str = bus_info_object['chukou']
            exits = exits_in_str.split("/")
            if len(exits) == 1:
                exits = exits_in_str.split("、")
            if len(exits) == 1:
                exits = exits_in_str.split(",")
            if len(exits) == 1:
                exits = exits_in_str.split("，")
            if len(exits) == 1:
                exits = exits_in_str.split(";")
            if len(exits) == 1:
                exits = exits_in_str.split("；")
            if len(exits) == 1:
                exits = exits_in_str.split(" ")
            if len(exits) == 1:
                exits = [exits_in_str]

            for exit in exits:
                if exit:
                    bus_out_info_array = bus_info_object.get('busOutInfo', [])
                    for bus_out_info in bus_out_info_array:
                        bus_lines_in_str = bus_out_info['busInfo']
                        bus_lines = bus_lines_in_str.split("、")
                        if len(bus_lines) == 1:
                            bus_lines = bus_lines_in_str.split(",")
                        if len(bus_lines) == 1:
                            bus_lines = bus_lines_in_str.split("，")
                        if len(bus_lines) == 1:
                            bus_lines = bus_lines_in_str.split(";")
                        if len(bus_lines) == 1:
                            bus_lines = bus_lines_in_str.split("；")
                        if len(bus_lines) == 1:
                            bus_lines = bus_lines_in_str.split(" ")
                        if len(bus_lines) == 1:
                            bus_lines = bus_lines_in_str.split(" ")
                        if len(bus_lines) == 1:
                            bus_lines = [bus_lines_in_str]
                        for bus_line in bus_lines:
                            if bus_line:
                                bus_name_and_line = bus_out_info['busName'] + " " + bus_line
                                if bus_name_and_line not in bus_infos_map:
                                    bus_info_id += 1
                                    bus_infos_map[bus_name_and_line] = bus_info_id
                                    bus_infos.append(BusInfo(bus_line, bus_out_info['busName']))
                                bus_exit_info = BusExitInfo(english_name, exit,
                                                            bus_infos_map[bus_name_and_line])
                                if bus_exit_info not in bus_exit_infos_set:
                                    bus_exit_infos_set.add(bus_exit_info)
                                    bus_exit_infos.append(bus_exit_info)

        landmark_info_array = station_data.get('out_info', [])
        for landmark_info_object in landmark_info_array:
            exits_in_str = landmark_info_object['outt']
            exits = exits_in_str.split("/")
            if len(exits) == 1:
                exits = exits_in_str.split("、")
            if len(exits) == 1:
                exits = exits_in_str.split(",")
            if len(exits) == 1:
                exits = exits_in_str.split("，")
            if len(exits) == 1:
                exits = exits_in_str.split(";")
            if len(exits) == 1:
                exits = exits_in_str.split("；")
            if len(exits) == 1:
                exits = exits_in_str.split(" ")
            if len(exits) == 1:
                exits = [exits_in_str]
            for exit in exits:
                if exit:
                    landmarks_in_str = landmark_info_object['textt']
                    landmarks = landmarks_in_str.split("、")
                    if len(landmarks) == 1:
                        landmarks = landmarks_in_str.split(",")
                    if len(landmarks) == 1:
                        landmarks = landmarks_in_str.split("，")
                    if len(landmarks) == 1:
                        landmarks = landmarks_in_str.split(";")
                    if len(landmarks) == 1:
                        landmarks = landmarks_in_str.split("；")
                    if len(landmarks) == 1:
                        landmarks = landmarks_in_str.split(" ")
                    if len(landmarks) == 1:
                        landmarks = landmarks_in_str.split(" ")
                    if len(landmarks) == 1:
                        landmarks = [landmarks_in_str]

                    for landmark in landmarks:
                        if landmark:
                            if landmark not in landmark_infos_map:
                                landmark_id += 1
                                landmark_infos_map[landmark] = landmark_id
                                landmark_infos.append(LandmarkInfo(landmark))
                            landmark_exit_info = LandmarkExitInfo(english_name, exit,
                                                                  landmark_infos_map[landmark])
                            if landmark_exit_info not in landmark_exit_infos_set:
                                landmark_exit_infos_set.add(landmark_exit_info)
                                landmark_exit_infos.append(landmark_exit_info)


class Line:
    def __init__(self, line_name, start_time, end_time, intro, mileage, color, first_opening, url):
        self.line_name = line_name
        self.start_time = start_time
        self.end_time = end_time
        self.intro = intro
        self.mileage = mileage
        self.color = color
        self.first_opening = first_opening
        self.url = url

    def __str__(self):
        return f"Line{{line_name={self.line_name}, start_time={self.start_time}, end_time={self.end_time}, " \
               f"intro={self.intro}, mileage={self.mileage}, color={self.color}, " \
               f"first_opening={self.first_opening}, url={self.url}}}"


class LineDetail:
    def __init__(self, line_name, station_name, station_order):
        self.line_name = line_name
        self.station_name = station_name
        self.station_order = station_order

    def __str__(self):
        return f"LineDetail{{line_name={self.line_name}, station_name={self.station_name}, " \
               f"station_order={self.station_order}}}"


lines = []
line_details = []

with open('resources/lines.json', 'r', encoding='utf-8') as file:
    data = json.load(file)

    for line_name, line_data in data.items():
        intro = line_data['intro']
        mileage = line_data['mileage']
        color = line_data['color']
        first_opening = datetime.strptime(line_data['first_opening'], '%Y-%m-%d')
        url = line_data['url']

        time_format = "%H:%M"
        start_time = datetime.strptime(line_data['start_time'], time_format).time()
        end_time = datetime.strptime(line_data['end_time'], time_format).time()

        line = Line(line_name, start_time, end_time, intro, mileage, color, first_opening, url)
        lines.append(line)

        line_station = line_data.get('stations', [])
        station_order = 0
        for line_station_name in line_station:
            station_order += 1
            line_detail = LineDetail(line_name, line_station_name, station_order)
            line_details.append(line_detail)


class Card:
    def __init__(self, code, money, create_time):
        self.code = code
        self.money = money
        self.create_time = create_time

    def __str__(self):
        return f"Card{{code={self.code}, money={self.money}, create_time={self.create_time}}}"


cards = []

with open('resources/cards.json', 'r', encoding='utf-8') as file:
    data = json.load(file)

for record in data:
    code = record['code']
    money = record['money']
    create_time = datetime.strptime(record['create_time'], '%Y-%m-%d %H:%M:%S')
    card = Card(code, money, create_time)
    cards.append(card)


class Passenger:
    def __init__(self, id_number, name, phone_number, gender, district):
        self.id_number = id_number
        self.name = name
        self.phone_number = phone_number
        self.gender = gender
        self.district = district

    def __str__(self):
        return f"Passenger{{id_number={self.id_number}, name={self.name}, phone_number={self.phone_number}, gender={self.gender}, district={self.district}}}"


passengers = []

with open('resources/passenger.json', 'r', encoding='utf-8') as file:
    data = json.load(file)

for record in data:
    id_number = record['id_number']
    name = record['name']
    phone_number = record['phone_number']
    gender = record['gender']
    district = record['district']
    passenger = Passenger(id_number, name, phone_number, gender, district)
    passengers.append(passenger)


class Ride:
    def __init__(self, user, start_station, end_station, price, start_time, end_time):
        self.user = user
        self.start_station = start_station
        self.end_station = end_station
        self.price = price
        self.start_time = start_time
        self.end_time = end_time

    def __str__(self):
        return f"User: {self.user}, start_station: {self.start_station}, end_station: {self.end_station}, price: {self.price}, start_time: {self.start_time}, end_time: {self.end_time}"


class RoutePricing:
    def __init__(self, start_station, end_station, price):
        self.start_station = start_station
        self.end_station = end_station
        self.price = price

    def __str__(self):
        return f"start_station: {self.start_station}, end_station: {self.end_station}, price: {self.price}"


class RideByIdNum:
    def __init__(self, user_num, start_time, end_time, pricing_id):
        self.user_num = user_num
        self.start_time = start_time
        self.end_time = end_time
        self.pricing_id = pricing_id

    def __str__(self):
        return f"user_num: {self.user_num}, start_time: {self.start_time}, end_time: {self.end_time}, pricing_id: {self.pricing_id}"


class RideByCardNum:
    def __init__(self, user_num, start_time, end_time, pricing_id):
        self.user_num = user_num
        self.start_time = start_time
        self.end_time = end_time
        self.pricing_id = pricing_id

    def __str__(self):
        return f"user_num: {self.user_num}, start_time: {self.start_time}, end_time: {self.end_time}, pricing_id: {self.pricing_id}"


rides = []
route_pricings = []
rides_by_id_num = []
rides_by_card_num = []

rides_json = json.load(open('resources/ride.json'))
route_id_map = {}
volume = int(0.01 * volume * 100000)
route_id = 0

for i in range(volume):
    ride_data = rides_json[i]
    ride = Ride(ride_data["user"], ride_data["start_station"], ride_data["end_station"], ride_data["price"],
                ride_data["start_time"], ride_data["end_time"])
    route = f"{ride_data['start_station']} -> {ride_data['end_station']}"

    if route not in route_id_map:
        route_pricing = RoutePricing(ride.start_station, ride.end_station, ride.price)
        route_pricings.append(route_pricing)
        route_id += 1
        route_id_map[route] = route_id

    if len(str(ride.user)) == 9:
        rides_by_card_num.append(RideByCardNum(ride.user, ride.start_time, ride.end_time, route_id_map[route]))
    else:
        rides_by_id_num.append(RideByIdNum(ride.user, ride.start_time, ride.end_time, route_id_map[route]))

read_end_time = time.time()
execution_time_ms = (read_end_time - read_start_time) * 1000
print("Read time: {:.2f} ms".format(execution_time_ms))

host = 'localhost'
db = 'cs307_project1_python'
user = 'layhenghok'
pwd = ''
port = '5432'

con = None
cur = None
try:
    con = psycopg2.connect(
        host=host,
        dbname=db,
        user=user,
        password=pwd,
        port=port)

    cur = con.cursor()

    write_start_time = time.time()

    for station in stations:
        sql = 'INSERT INTO station (english_name, chinese_name, district, intro) VALUES (%s, %s, %s, %s)'
        cur.execute(sql, (station.english_name, station.chinese_name, station.district, station.intro))
        statement_counts += 1

    for bus_info in bus_infos:
        sql = 'INSERT INTO bus_info (bus_line, bus_name) VALUES (%s, %s)'
        cur.execute(sql, (bus_info.bus_line, bus_info.bus_name))
        statement_counts += 1

    for bus_exit_info in bus_exit_infos:
        sql = 'INSERT INTO bus_exit_info (station_name, exit_gate, bus_info_id) VALUES (%s, %s, %s)'
        cur.execute(sql, (bus_exit_info.station_name, bus_exit_info.exit, bus_exit_info.bus_info_id))
        statement_counts += 1

    for landmark_info in landmark_infos:
        sql = 'INSERT INTO landmark_info (landmark) VALUES (%s)'
        cur.execute(sql, (landmark_info.landmark,))
        statement_counts += 1

    for landmark_exit_info in landmark_exit_infos:
        sql = 'INSERT INTO landmark_exit_info (station_name, exit_gate, landmark_id) VALUES (%s, %s, %s)'
        cur.execute(sql, (landmark_exit_info.station_name, landmark_exit_info.exit, landmark_exit_info.landmark_id))
        statement_counts += 1

    for line in lines:
        sql = 'INSERT INTO line (line_name, start_time, end_time, intro, mileage, color, first_opening, url) VALUES (%s, %s, %s, %s, %s, %s, %s, %s)'
        cur.execute(sql, (
            line.line_name, line.start_time, line.end_time, line.intro, line.mileage, line.color, line.first_opening,
            line.url))
        statement_counts += 1

    for line_detail in line_details:
        sql = 'INSERT INTO line_detail (line_name, station_name, station_order) VALUES (%s, %s, %s)'
        cur.execute(sql, (line_detail.line_name, line_detail.station_name, line_detail.station_order))
        statement_counts += 1

    for card in cards:
        sql = 'INSERT INTO card (code, money, create_time) VALUES (%s, %s, %s)'
        cur.execute(sql, (card.code, card.money, card.create_time))
        statement_counts += 1

    for passenger in passengers:
        sql = 'INSERT INTO passenger (id_num, name, phone_num, gender, district) VALUES (%s, %s, %s, %s, %s)'
        cur.execute(sql,
                    (passenger.id_number, passenger.name, passenger.phone_number, passenger.gender, passenger.district))
        statement_counts += 1

    for route_pricing in route_pricings:
        sql = 'INSERT INTO route_pricing (start_station, end_station, price) VALUES (%s, %s, %s)'
        cur.execute(sql, (route_pricing.start_station, route_pricing.end_station, route_pricing.price))
        statement_counts += 1

    for ride_by_id_num in rides_by_id_num:
        sql = 'INSERT INTO ride_by_id_num (user_num, start_time, end_time, pricing_id) VALUES (%s, %s, %s, %s)'
        cur.execute(sql, (
            ride_by_id_num.user_num, ride_by_id_num.start_time, ride_by_id_num.end_time, ride_by_id_num.pricing_id))
        statement_counts += 1

    for ride_by_card_num in rides_by_card_num:
        sql = 'INSERT INTO ride_by_card_num (user_num, start_time, end_time, pricing_id) VALUES (%s, %s, %s, %s)'
        cur.execute(sql, (
            ride_by_card_num.user_num, ride_by_card_num.start_time, ride_by_card_num.end_time,
            ride_by_card_num.pricing_id))
        statement_counts += 1

    con.commit()

    write_end_time = time.time()
    execution_time_ms = (write_end_time - write_start_time) * 1000
    print("Write time: {:.2f} ms".format(execution_time_ms))
    print("Total statements executed:", statement_counts, "statements")
    print("Throughput:", (statement_counts / (write_end_time - write_start_time), "statements/s"))
except Exception as error:
    print(error)
finally:
    if cur is not None:
        cur.close()
    if con is not None:
        con.close()
