import pandas as pd
import matplotlib.pyplot as plt

file_path1 = "cpuUsageResultsCreate.csv"
file_path2 = "cpuUsageResultsCreateCategories.csv"

data1 = pd.read_csv(file_path1)
data2 = pd.read_csv(file_path2)

print(data1)