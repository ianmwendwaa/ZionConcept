import mysql.connector as cnt
from mysql.connector.types import List
from mysql.connector import MySQLConnection

# ToDo:
# 1. Add new member
# 2. Update member details
# 3. Record meeting data (and update member contributions at any time)
db_config = {
    "user": "root",
    "passwd": "Ianmwendwa8435!!",
    "host": "localhost",
    "database": "elders_db"
}

def get_conn()->MySQLConnection:
    return cnt.connect(**db_config)

def get_all_members()->List:
    connection = get_conn()
    cursor = connection.cursor(dictionary=True)
    cursor.execute("SELECT* FROM elders_group_members")
    return cursor.fetchall()

def gen_unique_member_number(member_name: str, phone_number: str):
    first_name, last_name = member_name.split(" ")
    
    first_three_chars = first_name[0:3]
    second_name_initial = last_name[:1]
    
    trailing_phone_number_digits = phone_number[-4:]
    return first_three_chars.upper() + second_name_initial.upper() + "-" + trailing_phone_number_digits

def add_new_member():
    # while True:
    member_name = input("Enter member name: ")
    member_phone_number = input("Phone number: ")
            
    # Generate the unique member number (ELDR-FnSnFirstChar-4trailingnums)
    member_number = "ELDR-" + gen_unique_member_number(member_name, member_phone_number)
            
    # Commit
    if member_name == '' or member_phone_number == '':
        print("Please provide all inputs!")
        return
    
    try:
        conn = get_conn()
        cursor = conn.cursor()
        query = "INSERT INTO elders_group_members (member_name, member_phone_number, member_number) VALUES (%s, %s, %s)"
        cursor.execute(query, (member_name, member_phone_number, member_number))
        conn.commit()
        print(f"{member_name} has been added successfully!")
    except Exception as e:
        print(f"Encountered an error: {e}")
    cursor.close()
    conn.close()
    
def update_member_data(member_number: str):
    if not member_number:
        print("No member number was input!")
        return

    # Look up this member and update their data (it just dawned on me that I have never done this before)
    conn = get_conn()
    cursor = conn.cursor()
    return

def drop_member(member_number: str):
    conn = get_conn()
    cursor = conn.cursor()
    cursor.execute("SELECT (member_name) FROM elders_group_members WHERE (member_number = %s)", (member_number, ))
    member_name_to_be_dropped = cursor.fetchall()[0]
    query = "DELETE FROM elders_group_members WHERE (member_number = %s)"
    cursor.execute(query, (member_number, ))
    conn.commit()
    print(f"{member_name_to_be_dropped} deleted successfully!")
    cursor.close()
    conn.close()

def record_meeting_data():
    date_recorded = input("Enter date of recording: ")
    contribution_data = []
    while True:
        contributing_member_name = input("Enter name of contributor: ")
        # ToDo: Make this a float/double data type
        contribution_amount = int(input("Contribution: "))
        
        data = {"contributor": contributing_member_name, "amount": contribution_amount}
        contribution_data.append(data)
        continue_q = input("Add more contributions?: ").lower()
        if not continue_q == 'y':
            break
    
    emerging_issues = input("Emerging issues: ")
    contribution_amount = 0
    for amount_ in contribution_data:
        contribution_amount += amount_['amount']
    
    print(contribution_data)
    print(f"Contributed amount: {contribution_amount}")
    
    # Members who h
        
if __name__ == '__main__':
    record_meeting_data()
