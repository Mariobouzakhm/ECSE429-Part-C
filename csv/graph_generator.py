import pandas as pd
import matplotlib.pyplot as plt


file_path = [["cpuUsageResultsCreate.csv", "cpuUsageResultsCreateCategories.csv", 100, "CPU Usage (%)"], ["cpuUsageResultsModify.csv", "cpuUsageResultsModifyCategories.csv", 100, "CPU Usage (%)"], 
             ["cpuUsageResultsDelete.csv", "cpuUsageResultsDeleteCategories.csv", 100, "CPU Usage (%)"], ["memoryResultsCreate.csv", "memoryResultsCreateCategories.csv", 10e-3, "Memory Usage (kB)"],
             ["memoryResultsModify.csv", "memoryResultsModifyCategories.csv", 10e-3, "Memory Usage (kB)"], ["memoryResultsDelete.csv", "memoryResultsDeleteCategories.csv", 10e-3, "Memory Usage (kB)"],
             ["runTimeResultsTodoCreate.csv", "runTimeResultsTodoCreateCategories.csv", 1, "Transaction Time"], ["runTimeResultsTodoModify.csv", "runTimeResultsTodoModifyCategories.csv", 1, "Transaction Time"],
             ["runTimeResultsTodoDelete.csv", "runTimeResultsTodoDeleteCategories.csv", 1, "Transaction Time"]]

for l in file_path:

    file_path1 = l[0]
    file_path2 = l[1]
    factor = l[2]
    y_label = l[3]

    data1 = pd.read_csv(file_path1)
    data2 = pd.read_csv(file_path2)

    x = data1['result']
    y1 = (data1['description'] * factor).abs()
    y2 = (data2['description'] * factor).abs()

    # Create the plot
    plt.figure(figsize=(8, 6))  # Set figure size
    plt.plot(x, y1, label="Todo", marker='o')  # Plot first dataset
    plt.plot(x, y2, label="Category", marker='s')  # Plot second dataset

    # Customize the graph
    plt.title("Graph of Two Datasets")
    plt.xlabel("Number of Objects")
    plt.ylabel(y_label)
    plt.legend()  # Add legend
    plt.grid(True)  # Add grid

    # Show the graph
    plt.show()
